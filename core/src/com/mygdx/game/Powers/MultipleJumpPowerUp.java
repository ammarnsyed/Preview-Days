package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Player;

public class MultipleJumpPowerUp extends PowerUp {
    public MultipleJumpPowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        int multipleJumps = player.getMaxJumps();
        int numOfJumps = 2;
        player.setMaxJumps(multipleJumps + numOfJumps);
    }
}