package com.mygdx.game.Powers;


import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Player;

public class SizePowerUp extends PowerUp {
    public SizePowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        // Update the size of the player
        // Update the player's width and height accordingly
        player.setWidth(player.getWidth() / 2);
        player.setHeight(player.getHeight() / 2);
    }
}

