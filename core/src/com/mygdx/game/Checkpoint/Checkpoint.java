package com.mygdx.game.Checkpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.Sprites.Player;

public class Checkpoint {
    private float x, y;

    private World world;
    private Body body;
    private Fixture fixture;
    private String CheckName;
    private Boolean checkPoint;

    public Checkpoint(float x, float y, World world, String CheckName){
        this.world = world;
        body = BodyHelper.createBody(x, y, 1, 1, true, world);
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        fixture = body.getFixtureList().get(0);
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
        checkPoint = false;
    }

    public void printWhatever(){
        Gdx.app.log("Contact", "Hello");
    }

    public void update(Player player){
        if(checkPoint){
            world.destroyBody(body);
        }
    }

    private void setCheckPoint(){
        checkPoint = true;
    }

}
