package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Sprites.Player;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int width, height;
    private OrthographicCamera camera;
    public Boot(){
        INSTANCE = this;
    }


    @Override
    public void create() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, width, height);
        setScreen(new PlayScreen(camera));

    }
}
