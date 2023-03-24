package com.mygdx.game.GameLogic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.GameLogic.Helper.Constants;

public class Checkpoint {
    private int x;
    private int y;

    private static boolean checkPointReached;
    private static int setX;
    private static int setY;

    public Checkpoint(int x, int y, Body body){
        Fixture fixture = body.getFixtureList().get(0);
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
        this.x = x;
        this.y = y;
        checkPointReached = false;
    }


    public void changeSpawn(){
        checkPointReached = true;
        setX = x;
        setY = y;
        Gdx.app.log("Changing checkpoint", setX + " " + setY);
    }

    public static int getSpawnX(){
        if(!checkPointReached){
            return 3500;
        }
        else{
            return setX;
        }
    }

    public static int getSpawnY(){
        if(!checkPointReached){
            return 4880;
        }
        else{
            return setY;
        }

    }
    

}
