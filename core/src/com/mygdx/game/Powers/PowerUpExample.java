package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Player;

public class PowerUpExample extends PowerUp {
    public PowerUpExample(float x, float y, World world){
        super(x, y, world);
    }

    /**
     * Override this method with your power up implementation to overcome the obstacle.
     * @param player refers to the player itself. See what kind of methods are available to you to help you
     *               come up with a unique ways to traverse through the level!
     */
    @Override
    public void powerUpActivate(Player player) {
        player.setWidth(player.getWidth()/2);
        player.setHeight(player.getHeight()/2);
    }
}
