package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int width, height;
    private OrthographicCamera camera;
    public Boot(){
        INSTANCE = this;
    }
    private ScreenAdapter currentScreen;

    @Override
    public void create() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, width, height);
        startGame();
    }

    public void startGame(){
        currentScreen = new PlayScreen(camera);
        setScreen(currentScreen);
    }

    public void endGame(){
        currentScreen = new EndScreen();
        setScreen(currentScreen);
    }

    public void disposeCurrentScreen(){
        currentScreen.dispose();
        Gdx.app.log("Disposing Screen:", currentScreen.getClass().getName());
    }
}
