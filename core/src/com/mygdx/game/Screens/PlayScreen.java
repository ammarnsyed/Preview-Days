package com.mygdx.game.Screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;

import static com.mygdx.game.Helper.Constants.PPM;

public class PlayScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    Texture img;
    private gStateManager gsm;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Vector3 cameraAnchor;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Array<NPC> NPCs;

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
    private Image image1;

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
        playerLives = player.getLives();
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
        if(Gdx.input.isKeyPressed(Input.Keys.P)){
            player.setPaused();
            cameraAnchor = camera.position;
            cameraAnchor.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
            cameraAnchor.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        }
    }

    public void updatePause(float delta){
        cameraAnchored();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.U)){
            player.setUnPaused();
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

    private void cameraAnchored(){
      camera.position.set(cameraAnchor);
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
      if(player.getPaused()) {
          float deltaP = delta;
          updatePause(deltaP);
      }else{
          this.update(delta);

          Gdx.gl.glClearColor(0, 0, 0, 1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

          orthogonalTiledMapRenderer.render();
          batch.begin();

          deltaTime = Gdx.graphics.getDeltaTime();
          deltaTime -= delta;
        /*font.draw(batch, "Score:  " + score, 3800,5000);
        font.draw(batch, "Time: " + deltaTime,3800,4950);
        font.getData().setScale(3f);*/

          //Render objects such as characters and walls
          player.render(batch);

          jumpPowerUpTest.render(batch);
          speedPowerUpTest.render(batch);
          sizePowerUpTest.render(batch);
          multipleJumpPowerUpTest.render(batch);
          antiGravityPowerUpTest.render(batch);

          for (NPC npc : NPCs) {
              npc.render(batch);
          }

          //font.draw(batch, "Time remaining: " + (int) timeRemaining, 1000, 1000);
          batch.end();
          box2DDebugRenderer.render(world, camera.combined.scl(PPM));

          gsm.update(Gdx.graphics.getDeltaTime());
          gsm.render(batch);
          if (player.isDead() && player.getStateTimer() > 3) {
              Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
              Boot.INSTANCE.create();
          }
          Label sc = new Label("Score: ".concat(String.valueOf(score)), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
          Label tm = new Label("Time: ".concat(String.valueOf(deltaTime)), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


          if (playerLives + 1 == 3) {
              image1 = new Image(new Texture("3hp.png"));
          } else if (playerLives + 1 == 2) {
              image1 = new Image(new Texture("2hp.png"));
          } else if (playerLives + 1 == 1) {
              image1 = new Image(new Texture("1hp.png"));
          } else {
              image1 = new Image(new Texture("dead.png"));
          }
          Table newTable = new Table();
          newTable.setFillParent(true);
          sc.setFontScale(3f, 3f);
          tm.setFontScale(3f, 3f);
          newTable.top();
          newTable.left();
          newTable.padLeft(20f);
          newTable.add(image1);
          newTable.row();
          newTable.add(sc);
          newTable.row();
          newTable.add(tm);

          stage.addActor(newTable);
          stage.draw();
      }
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
