package com.mygdx.game.GameLogic.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.GameLogic.PlayScreen;
import com.mygdx.game.GameLogic.Player;

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
        if(Player.isDead){
            Gdx.app.log("Screen:", "Dead");
            setScreen(new EndScreen(camera));
        } else {
            Gdx.app.log("Screen:", "Alive");
            setScreen(new PlayScreen(camera));
        }

    }
}
