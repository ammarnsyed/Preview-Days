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
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.TileMapHelper;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.States.MenuState;
import com.mygdx.game.States.gStateManager;

import javax.swing.*;

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



    public PlayScreen(OrthographicCamera camera){
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f ), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.mapSetup();

        Body playerBody = BodyHelper.createBody(96, 64, 1, 1, false, world);
        player = new Player(1, 1, playerBody);

    }

    private void update(){
        world.step(1/60f, 6, 2);
        cameraUpdate();
        player.update();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * PPM * 10)/10f;
        position.y = Math.round(player.getBody().getPosition().y * PPM * 10)/10f;
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
        this.update();

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
