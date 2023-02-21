package com.mygdx.game.Powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class SpeedPowerUp extends PowerUp{

    public SpeedPowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        Gdx.app.log("Speed Power", "Collision");
        float speed = player.getSpeed();
        int speedIncrease = 3;
        player.setSpeed(speed + speedIncrease);
        activated = true;
        timer = 0f;
    }

    @Override
    public void consume() {
        toDestroy = true;
    }
}
