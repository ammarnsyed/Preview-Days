package com.mygdx.game.GameLogic.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.GameLogic.Checkpoint.Spawner;
import com.mygdx.game.GameLogic.PlayScreen;
import com.mygdx.game.GameLogic.Player;
import com.mygdx.game.GameLogic.SoundEffects;

public class EndScreen extends ScreenAdapter {
    private Stage stage;
    private PlayScreen screen;
    private Player player;
    TextButton button;
    TextButton button1;
    TextButton button2;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    Preferences prefs;


    public EndScreen(OrthographicCamera camera) {
        this.stage = new Stage();
        player = new Player();
        font = new BitmapFont();
        skin = new Skin();

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton("Save and exit", textButtonStyle);
        button.getLabel().setFontScale(5f, 5f);

        button1 = new TextButton("Play Again!", textButtonStyle);
        button1.getLabel().setFontScale(5f, 5f);
        button2 = new TextButton("Restart", textButtonStyle);
        button2.getLabel().setFontScale(5f, 5f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                Gdx.app.log("Game", "Saved");
            }
        });

        button1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Game", "Continue");
                Sound startSound = SoundEffects.getUISE();
                startSound.play(0.5f);
                player.setIsDead(false);
                Boot.INSTANCE.create();
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs = Gdx.app.getPreferences("My Preferences");
                Gdx.app.log("Game", "Refreshed");
                prefs.clear();
                prefs.flush();
                Spawner.resetSpawn();
                Sound startSound = SoundEffects.getUISE();
                startSound.play(0.5f);
                player.setIsDead(false);
                Boot.INSTANCE.create();
            }
        });

        Table newTable = new Table();
        newTable.setFillParent(true);
        newTable.row();
        newTable.add(button1).padTop(5f);
        newTable.row();
        newTable.add(button2).padTop(5f);
        newTable.row();
        newTable.add(button).padTop(5f);
        newTable.center();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(newTable);
    }

    @Override
    public void render(float delta) {
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
