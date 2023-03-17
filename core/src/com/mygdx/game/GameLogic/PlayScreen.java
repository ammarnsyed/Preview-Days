package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Checkpoint.Checkpoint;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.Constants;
import com.mygdx.game.GameLogic.Helper.TileMapHelper;
import com.mygdx.game.GameLogic.Screens.Boot;
import com.mygdx.game.GameLogic.States.MenuState;
import com.mygdx.game.Powers.*;
import com.mygdx.game.GameLogic.States.gStateManager;

import java.util.ArrayList;

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
    private ArrayList<MapCoordinate> powerUpLocations;
    private ArrayList<PowerUp> actualPowerUps;
    private PowerUpHelper powerHelper;

    private Player player;

    private int playerLives;

    private JumpPowerUp jumpPowerUpTest;
    private SpeedPowerUp speedPowerUpTest;
    private SizePowerUp sizePowerUpTest;
    private Checkpoint Spawn;
    private Checkpoint Random;
    private int playerX;
    private MultipleJumpPowerUp multipleJumpPowerUpTest;
    private AntiGravityPowerUp antiGravityPowerUpTest;

    private Stage stage;
    private TextureAtlas atlas;

    private BitmapFont font;

    private float deltaTime = 10;
    private float score = 0;


  public PlayScreen(OrthographicCamera camera){
        this.font = new BitmapFont();

        this.stage = new Stage();

        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();

        //powerup handling
        powerHelper = new PowerUpHelper(tileMapHelper.getPowerUps(), world);
        actualPowerUps = powerHelper.getPowerUps();

        playerX = Checkpoint.pointX;
        //3300 4580 start coords
        Body playerBody = BodyHelper.createRectangularBody(Checkpoint.spawnX(), Checkpoint.spawnY(), 0.5f, 1, false, world);
        player = new Player(1, 1, playerBody, getWorld());

        NPCs = tileMapHelper.getNPCs();



        world.setContactListener(new WorldContactListener());

        //Setting three different Power ups to test collision detection for all 5668 5846 / 3300 4880
//        jumpPowerUpTest = new JumpPowerUp(5700, 4600, world);
//        speedPowerUpTest = new SpeedPowerUp(9236, 5488, world);
//        sizePowerUpTest = new SizePowerUp(3300, 4880, world);
//        multipleJumpPowerUpTest = new MultipleJumpPowerUp(3300, 6000, world);
//        antiGravityPowerUpTest = new AntiGravityPowerUp(5668, 5846, world);


        //Checkpoints
        Spawn = new Checkpoint(5800, 5080, world, "Spawn");
        Random = new Checkpoint(6500, 4600, world, "Random");

    }

    private void update(float delta){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        playerLives = player.getLives();
        player.update(delta);
        for(NPC npc : NPCs){
            npc.update(delta);
        }

        for(PowerUp power : actualPowerUps){
            power.update(player, delta);
        }

//        jumpPowerUpTest.update(player, delta);
//        speedPowerUpTest.update(player, delta);
//        sizePowerUpTest.update(player, delta);

        Checkpoint.spawnX();
        Checkpoint.spawnY();

//        multipleJumpPowerUpTest.update(player, delta);
//        antiGravityPowerUpTest.update(player, delta);

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){
        Vector3 position = camera.position;
        if(!player.isDead()) {
            position.x = Math.round(player.getBody().getPosition().x * Constants.PPM * 10) / 10f;
            position.y = Math.round(player.getBody().getPosition().y * Constants.PPM * 10) / 10f;
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
        deltaTime -= delta;
        font.draw(batch, "Score:  " + score, 3800,5000);
        font.draw(batch, "Time: " + deltaTime,3800,4950);
        font.getData().setScale(3f);


        

        //Render objects such as characters and walls
        player.render(batch);

//        jumpPowerUpTest.render(batch);
//        speedPowerUpTest.render(batch);
//        sizePowerUpTest.render(batch);
//        multipleJumpPowerUpTest.render(batch);
//        antiGravityPowerUpTest.render(batch);

        for(NPC npc : NPCs){
            npc.render(batch);
        }

        for(PowerUp power : actualPowerUps){
            power.render(batch);
        }

        //font.draw(batch, "Time remaining: " + (int) timeRemaining, 1000, 1000);
        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Constants.PPM));

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        if(player.isDead() && player.getStateTimer() > 3){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Boot.INSTANCE.create();
        }
        Label livesCounter = new Label("Lives: ".concat(String.valueOf(playerLives+1)), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        livesCounter.setFontScale(4f, 4f);

        Table newTable = new Table();
        newTable.setFillParent(true);
        newTable.add(livesCounter);
        newTable.right();
        newTable.top();
        newTable.padRight(20f);
        stage.addActor(newTable);
        stage.draw();
    }

    public void setPlayer(Player player){
        this.player = player;
    }
    public World getWorld() {
        return world;
    }
    public TextureAtlas getAtlas(){return atlas;}
    private String formatTime(float time) {
      int minutes = (int)time / 60;
      int seconds = (int)time % 60;
      return String.format("%02d:%02d", minutes, seconds);
  }
}
