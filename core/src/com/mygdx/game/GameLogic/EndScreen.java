package com.mygdx.game.GameLogic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.GameLogic.Boot;
import com.mygdx.game.GameLogic.Player;
import com.mygdx.game.GameLogic.SoundEffects;

public class EndScreen extends ScreenAdapter {
    private Stage stage;
    private Player player;


    public EndScreen() {
        this.stage = new Stage();
        player = new Player();

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
            Sound startSound = SoundEffects.getUISE();
            startSound.play(0.5f);
            player.setIsDead(false);
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
