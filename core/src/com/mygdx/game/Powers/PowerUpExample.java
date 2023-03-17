package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Player;

public class PowerUpExample extends PowerUp {
    public PowerUpExample(float x, float y, World world){
        super(x, y, world);
    }

    @Override
    public void powerUpActivate(Player player) {
        player.setJumpForce(20);

    }
}
