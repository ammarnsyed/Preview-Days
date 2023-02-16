package com.mygdx.game.Powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

public class SpeedPowerUp extends PowerUp{

    public SpeedPowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate() {
        Gdx.app.log("Speed Power", "Collision");
    }
}
