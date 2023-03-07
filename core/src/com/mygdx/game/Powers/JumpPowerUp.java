package com.mygdx.game.Powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Player;

public class JumpPowerUp extends PowerUp {
    public JumpPowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        int jumpPower = player.getJumpForce();
        int jumpIncrease = 10;
        player.setJumpForce(jumpPower + jumpIncrease);
    }
}
