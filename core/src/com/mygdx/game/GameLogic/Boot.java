package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {

    public static Boot INSTANCE;
    private int width, height;
    private OrthographicCamera camera;
    private Player player;
    public Boot(){
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, width, height);
        player = new Player();

        if(player.isDead()){
            Gdx.app.log("Screen:", "Dead");
            setScreen(new EndScreen());
        } else {
            Gdx.app.log("Screen:", "Alive");
            setScreen(new PlayScreen(camera));
        }
    }
}
