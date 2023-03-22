package com.mygdx.game.GameLogic.Checkpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.Constants;

public class Checkpoint {
    private int x;
    private int y;

    private World world;
    private Body body;
    private Fixture fixture;
    private String CheckName;
    private static Boolean checkPoint = false;
    private static String pointName;
    private static int setX;
    private static int setY;
    private static int pointX;
    private static int pointY;


    public Checkpoint(int x, int y, World world, String CheckName){
        this.world = world;
        body = BodyHelper.createRectangularBody(x, y, 1, 1, true, world);
        fixture = body.getFixtureList().get(0);
        this.x = x;
        this.y = y;
        this.CheckName = CheckName;
        checkPoint = false;
        pointName = null;
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
    }

    public Checkpoint(){
    }

    public void printContact(){
        Gdx.app.log("Contact", "Checkpoint");
        checkPoint = true;
        setX = this.x;
        setY = this.y;
        pointName = this.CheckName;
    }


    public String getCheckName() {
        return pointName;
    }


    public float spawnX(){
        if(!checkPoint){
            pointX = 3500;
        }else{
            pointX = setX;
        }
        return pointX;
    }
    public float spawnY(){
        if(!checkPoint){
            pointY = 4880;
        }else{
            pointY = setY;
        }
        return pointY;
    }
}
