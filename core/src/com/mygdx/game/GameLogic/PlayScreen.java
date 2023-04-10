package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Checkpoint.Checkpoint;
import com.mygdx.game.GameLogic.Checkpoint.Spawner;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.TileMapHelper;
import com.mygdx.game.GameLogic.States.MenuState;
import com.mygdx.game.Powers.*;
import com.mygdx.game.GameLogic.States.gStateManager;

import static com.mygdx.game.GameLogic.Helper.Constants.PPM;

import java.util.ArrayList;

public class PlayScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    Texture img;
    private gStateManager gsm;
    private World world;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Array<NPC> NPCs;
    private Array<Checkpoint> checkpoints;
    private ArrayList<PowerUp> actualPowerUps;
    private ClassToList powerHelper;

    private Player player;
    private Hud hud;

    private boolean isPaused;
    Preferences prefs;

    public PlayScreen(OrthographicCamera camera){
        SoundEffects.startMainMusic();
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();

        // Retrieve saved checkpoint coordinates
        prefs = Gdx.app.getPreferences("My Preferences");
        float checkpointX = prefs.getFloat("lastCheckpointX", Spawner.getInstance().getSpawnX());
        float checkpointY = prefs.getFloat("lastCheckpointY", Spawner.getInstance().getSpawnY());

        // Create player body at checkpoint coordinates
        Body playerBody = BodyHelper.createRectangularBody(checkpointX, checkpointY, 0.5f, 1, false, world);
        player = new Player(1, 1, playerBody);

        powerHelper = new ClassToList(tileMapHelper.getPowerUps(), world);
        actualPowerUps = powerHelper.getPowerUps();
        NPCs = tileMapHelper.getNPCs();
        checkpoints = tileMapHelper.getCheckpoints();

        this.hud = new Hud(batch, player);
        loadPlayerProgress();

        world.setContactListener(new WorldContactListener());

    }


    private void update(float delta){
        if (!isPaused) {
            world.step(1/60f, 6, 2);
            cameraUpdate();
            player.update(delta);
            hud.update(delta, player, actualPowerUps);
            for(NPC npc : NPCs){
                npc.update(delta);
            }
            for(PowerUp power : actualPowerUps){
                power.update(player, delta);
            }

            batch.setProjectionMatrix(camera.combined);
            orthogonalTiledMapRenderer.setView(camera);

            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                exitGame();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.P)){
                Sound pauseSound = SoundEffects.getPauseSE();
                pauseSound.play(0.5f);
                SoundEffects.changeMainMusicVolume(0.25f);
                player.setPaused();
                isPaused = true;
            }
        }

        else {
            updatePaused();
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                exitGame();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.U)) {
                updateResume();
                Sound resumeSE = SoundEffects.getResumeSE();
                SoundEffects.changeMainMusicVolume(0.5f);
                resumeSE.play(0.5f);
            }
        }
    }

    public void updatePaused(){
        hud.updatePause();
        hud.buttonDetect(this);
    }

    public void updateResume(){
        player.setUnPaused();
        isPaused = false;
        hud.updateResume();
    }

    public void exitGame(){
        Gdx.app.exit();
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

    public void savePlayerProgress() {
        prefs = Gdx.app.getPreferences("My Preferences");
        prefs.putInteger("lastCheckpointX", (int) Spawner.getInstance().getSpawnX());
        prefs.putInteger("lastCheckpointY", (int) Spawner.getInstance().getSpawnY());
        prefs.putFloat("timeTaken", hud.getTime());
        prefs.flush();
    }

    private void loadPlayerProgress() {
        prefs = Gdx.app.getPreferences("My Preferences");
        int lastCheckpointX = prefs.getInteger("lastCheckpointX", 3500);
        int lastCheckpointY = prefs.getInteger("lastCheckpointY", 4880);
        float timeTaken = prefs.getFloat("timeTaken", 0);
        Spawner.getInstance().setSpawn(lastCheckpointX, lastCheckpointY);
        hud.setTime(timeTaken);
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

        //Render objects such as characters and walls
        player.render(batch);

        for (NPC npc : NPCs) {
            npc.render(batch);
        }
        for(PowerUp power : actualPowerUps){
            power.render(batch);
        }

        hud.stage.draw();
        hud.stage.act();
        batch.end();

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
        if (player.isDead() && player.getStateTimer() > 3) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Boot.INSTANCE.disposeCurrentScreen();
            Boot.INSTANCE.endGame();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public World getWorld() {
        return world;
    }

}
