package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Sprites.Player;

public class EndScreen extends ScreenAdapter {
    private Stage stage;
    private PlayScreen screen;


    public EndScreen(OrthographicCamera camera) {
        this.stage = new Stage();

        Label EndScreenLabel = new Label("Game Over", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label Replay = new Label("Play Again?", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        EndScreenLabel.setFontScale(5f, 5f);
        Replay.setFontScale(5f, 5f);

        Table newTable = new Table();
        newTable.setFillParent(true);
        newTable.add(EndScreenLabel);
        newTable.row();
        newTable.add(Replay).padTop(5f);
        newTable.center();
        stage.addActor(newTable);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            Player.isDead = false;
            Boot.INSTANCE.create();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
