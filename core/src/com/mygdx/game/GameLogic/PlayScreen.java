package com.mygdx.game.GameLogic;

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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.TileMapHelper;
import com.mygdx.game.GameLogic.Screens.Boot;
import com.mygdx.game.GameLogic.States.MenuState;
import com.mygdx.game.Powers.*;
import com.mygdx.game.GameLogic.States.gStateManager;

import static com.mygdx.game.GameLogic.Helper.Constants.PPM;

import java.util.ArrayList;

public class PlayScreen extends ScreenAdapter {
    private BitmapFont font;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    Texture img;
    private gStateManager gsm;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Array<NPC> NPCs;
    private Array<Checkpoint> checkpoints;
    private ArrayList<MapCoordinate> powerUpLocations;
    private ArrayList<PowerUp> actualPowerUps;
    private PowerUpHelper powerHelper;

    private Player player;
    private Hud hud;
    private int playerLives;


    private Checkpoint Spawn;
    private Checkpoint Random;
    private int playerX;

    private Stage stage;
    private TextureAtlas atlas;
    private Image image1;
    private boolean isPaused;

    private float deltaTime = 10;
    private float score = 0;



  public PlayScreen(OrthographicCamera camera){
        this.font = new BitmapFont();
        SoundEffects.startMainMusic();
        this.stage = new Stage();
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();


        powerHelper = new PowerUpHelper(tileMapHelper.getPowerUps(), world);
        actualPowerUps = powerHelper.getPowerUps();
        NPCs = tileMapHelper.getNPCs();
        checkpoints = tileMapHelper.getCheckpoints();
        Checkpoint check = checkpoints.get(0);

        Gdx.app.log("Checkpoint Location", Checkpoint.getSpawnX() + " " + Checkpoint.getSpawnY());
        Body playerBody = BodyHelper.createRectangularBody(Checkpoint.getSpawnX(), Checkpoint.getSpawnY(), 0.5f, 1, false, world);

        player = new Player(1, 1, playerBody, getWorld());



        this.hud = new Hud(batch, player);


        world.setContactListener(new WorldContactListener());




    }

    private void update(float delta){
        if (!isPaused) {
            world.step(1/60f, 6, 2);
            cameraUpdate();
            player.update(delta);
            hud.update(delta, player);
            for(NPC npc : NPCs){
                npc.update(delta);
            }
            for(PowerUp power : actualPowerUps){
                power.update(player, delta);
            }




            batch.setProjectionMatrix(camera.combined);
            orthogonalTiledMapRenderer.setView(camera);

            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                Gdx.app.exit();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.P)){
                player.setPaused();
                isPaused = true;
            }
        }

        else {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                Gdx.app.exit();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                player.setUnPaused();
                isPaused = false;
            }
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

          Gdx.gl.glClearColor(0, 0, 0, 1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

          orthogonalTiledMapRenderer.render();
          batch.begin();
          deltaTime = Gdx.graphics.getDeltaTime();
          deltaTime -= delta;

          //Render objects such as characters and walls
          player.render(batch);


          for (NPC npc : NPCs) {
              npc.render(batch);
          }
          for(PowerUp power : actualPowerUps){
            power.render(batch);
          }


          hud.stage.draw();
          batch.end();
          box2DDebugRenderer.render(world, camera.combined.scl(PPM));

          gsm.update(Gdx.graphics.getDeltaTime());
          gsm.render(batch);
          if (player.isDead() && player.getStateTimer() > 3) {
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
