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
        float speed = player.getSpeed();
        int speedIncrease = 10;
        player.setSpeed(speed + speedIncrease);
    }
}
