package com.mygdx.game.Checkpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.Constants;

public class Checkpoint {
    private int x;
    private int y;

    private World world;
    private Body body;
    private Fixture fixture;
    private String CheckName;
    private static Boolean checkPoint = false;
    public static String pointName;
    public static int setX;
    public static int setY;
    public static int pointX;
    public static int pointY;


    public Checkpoint(int x, int y, World world, String CheckName){
        this.world = world;
        body = BodyHelper.createBody(x, y, 1, 1, true, world);
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

    public void printContact(){
        Gdx.app.log("Contact", "Checkpoint");
        checkPoint = true;
        setX = this.x;
        setY = this.y;
        pointName = this.CheckName;
    }

    public void update(Checkpoint checkpoint){
    }

    public String printX(){
        return String.valueOf(x);
    }

    public int returnX(){
        return x;
    }
    public void getX() {
        checkPoint = true;
    }

    public String getCheckName() {
        return pointName;
    }

    public static Checkpoint getCheckpoint(String pointName, Array<Checkpoint> checkpoints){
        for(Checkpoint check : checkpoints){
            if(check.getCheckName().equals(pointName)){
                return check;
            }
        }
        return null;
    }

    public static float spawnX(){
        if(!checkPoint){
            pointX = 3500;
        }else{
            pointX = setX;
        }
        return pointX;
    }
    public static float spawnY(){
        if(!checkPoint){
            pointY = 4880;
        }else{
            pointY = setY;
        }
        return pointY;
    }
}
