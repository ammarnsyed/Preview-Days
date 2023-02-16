package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Helper.BodyHelper;

public abstract class PowerUp {
    protected float x, y;
    protected Body body;
    protected Fixture fixture;

    public PowerUp(float x, float y, World world){
        body = BodyHelper.createBody(x, y, 1, 1, true, world);
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        fixture = body.getFixtureList().get(0);
    }

    public abstract void powerUpActivate();

    public Body getBody(){
        return body;
    }
}
