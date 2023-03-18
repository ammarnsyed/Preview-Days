package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Player;

public class AntiGravityPowerUp extends PowerUp {
    public AntiGravityPowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        // Flip gravity
        player.setGravityScale(-1);
    }
}
