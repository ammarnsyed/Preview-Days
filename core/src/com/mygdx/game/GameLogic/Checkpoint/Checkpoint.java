package com.mygdx.game.GameLogic.Checkpoint;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.GameLogic.Helper.Constants;

public class Checkpoint {
    private int x;
    private int y;


    public Checkpoint(int x, int y, Body body){
        Fixture fixture = body.getFixtureList().get(0);
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
        this.x = x;
        this.y = y;
    }


    public void changeSpawn(){
        Spawner.getInstance().setSpawn(x, y);
    }

}
