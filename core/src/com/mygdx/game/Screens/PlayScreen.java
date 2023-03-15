package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Checkpoint.Checkpoint;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.TileMapHelper;
import com.mygdx.game.Helper.WorldContactListener;
import com.mygdx.game.Powers.*;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.NPC;
import com.mygdx.game.States.MenuState;
import com.mygdx.game.States.gStateManager;

import java.util.ArrayList;

import static com.mygdx.game.Helper.Constants.PPM;

public class PlayScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    Texture img;
    private gStateManager gsm;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Array<NPC> NPCs;

    private Player player;


    private JumpPowerUp jumpPowerUpTest;
    private SpeedPowerUp speedPowerUpTest;
    private SizePowerUp sizePowerUpTest;
    private Checkpoint Spawn;
    private Checkpoint Random;
    private int playerX;
    private MultipleJumpPowerUp multipleJumpPowerUpTest;
    private AntiGravityPowerUp antiGravityPowerUpTest;

    private TextureAtlas atlas;

    private float deltaTime = 10;
    private float score = 0;
    private BitmapFont font;

    public PlayScreen(OrthographicCamera camera){

        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();

        playerX = Checkpoint.pointX;
        //3300 4580 start coords
        Body playerBody = BodyHelper.createRectangularBody(Checkpoint.spawnX(), Checkpoint.spawnY(), 0.5f, 1, false, world);
        player = new Player(1, 1, playerBody, getWorld());

        NPCs = tileMapHelper.getNPCs();


        world.setContactListener(new WorldContactListener());

        //Setting three different Power ups to test collision detection for all
        jumpPowerUpTest = new JumpPowerUp(5700, 4600, world);
        speedPowerUpTest = new SpeedPowerUp(9236, 5488, world);
        sizePowerUpTest = new SizePowerUp(5668, 5846, world);

        //Checkpoints
        Spawn = new Checkpoint(5800, 5080, world, "Spawn");
        Random = new Checkpoint(6500, 4600, world, "Random");

        multipleJumpPowerUpTest = new MultipleJumpPowerUp(3300, 6000, world);
        antiGravityPowerUpTest = new AntiGravityPowerUp(3300, 4880, world);

    }

    private void update(float delta){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        player.update(delta);
        for(NPC npc : NPCs){
            npc.update(delta);
        }

        jumpPowerUpTest.update(player, delta);
        speedPowerUpTest.update(player, delta);
        sizePowerUpTest.update(player, delta);

        Checkpoint.spawnX();
        Checkpoint.spawnY();

        multipleJumpPowerUpTest.update(player, delta);
        antiGravityPowerUpTest.update(player, delta);

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){
        Vector3 position = camera.position;
        if(!player.isDead()) {
            position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
            position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        }
        camera.position.set(position);
        camera.update();
    }


    @Override
    public void show(){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        gsm = new gStateManager();
        Gdx.gl.glClearColor(0,0,0,2);
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render(float delta){
        this.update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();
        deltaTime = Gdx.graphics.getDeltaTime();
        font.draw(batch, "Score: " + score,4000,4000);
        font.draw(batch, "Time: " + deltaTime,4000,3950);
        font.getData().setScale(2f);
        //Render objects such as characters and walls
        player.render(batch);

        jumpPowerUpTest.render(batch);
        speedPowerUpTest.render(batch);
        sizePowerUpTest.render(batch);
        multipleJumpPowerUpTest.render(batch);
        antiGravityPowerUpTest.render(batch);

        for(NPC npc : NPCs){
            npc.render(batch);
        }

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        if(player.isDead() && player.getStateTimer() > 3){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Boot.INSTANCE.create();
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public World getWorld() {
        return world;
    }
    public TextureAtlas getAtlas(){return atlas;}
}
