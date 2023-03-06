package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.TileMapHelper;
import com.mygdx.game.Helper.WorldContactListener;
import com.mygdx.game.Powers.JumpPowerUp;
import com.mygdx.game.Powers.SizePowerUp;
import com.mygdx.game.Powers.SpeedPowerUp;
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

    private Player player;
    private NPC npc;
    private Array<NPC> npcList = new Array<NPC>();
    private Array<NPC> npcList2 = new Array<NPC>();
    private Array<NPC> npcList3 = new Array<NPC>();
    private JumpPowerUp jumpPowerUpTest;
    private SpeedPowerUp speedPowerUpTest;
    private SizePowerUp sizePowerUpTest;


    public PlayScreen(OrthographicCamera camera){
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();

        Body playerBody = BodyHelper.createBody(6000, 300, 1, 1, false, world);
        player = new Player(1, 1, playerBody);

        //first npc obstacle
        int npcSectionOne = 2432;
        for(int i = 0; i < 2; i++){
            npcList.add(new NPC(1,1,BodyHelper.createBody(npcSectionOne+704*i,64,1,1,false,world)));
        }
        //second npc obstacle section
        int npcSectionTwo = 3564;
        for(int i = 0; i < 1; i++){
            npcList2.add(new NPC(1,1,BodyHelper.createBody(npcSectionTwo,64,1,1,false,world)));
        }
        //third npc obstacle section
        int npcSectionThree = 4720;
        for(int i = 0; i < 4; i++){
            npcList3.add(new NPC(1,1,BodyHelper.createBody(npcSectionThree+320*i,64,1,1,false,world)));
        }

        world.setContactListener(new WorldContactListener());

        //Setting three different Power ups to test collision detection for all
        //jumpPowerUpTest = new JumpPowerUp(500, 500, world);
        speedPowerUpTest = new SpeedPowerUp(6000, 1024, world);
        sizePowerUpTest = new SizePowerUp(2368, 1266, world);

    }

    private void update(float delta){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        player.update();
        for(NPC test : npcList){
            test.update();
        }
        for(NPC test : npcList2){
            test.update();
        }
        for(NPC test : npcList3){
            test.update();
        }



        jumpPowerUpTest.update(player, delta);
        speedPowerUpTest.update(player, delta);
        sizePowerUpTest.update(player, delta);


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
      Gdx.gl.glClearColor(0,0,0,1);
      gsm.push(new MenuState(gsm));
    }

    @Override
    public void render(float delta){
        this.update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();
        //Render objects such as characters and walls

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public World getWorld() {
        return world;
    }
}
