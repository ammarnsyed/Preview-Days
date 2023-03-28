package com.mygdx.game.GameLogic.Checkpoint;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.GameLogic.Helper.Constants;
import com.mygdx.game.GameLogic.PlayScreen;

public class Checkpoint {
    private int x;
    private int y;
    private PlayScreen playScreen;

    public Checkpoint(int x, int y, Body body, PlayScreen playScreen) {
        Fixture fixture = body.getFixtureList().get(0);
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
        this.x = x;
        this.y = y;
        this.playScreen = playScreen;
    }

    public void changeSpawn() {
        Spawner.getInstance().setSpawn(x, y);
        playScreen.savePlayerProgress();
    }
}

