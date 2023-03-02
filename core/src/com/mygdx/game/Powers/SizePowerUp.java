package com.mygdx.game.Powers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.Player;

public class SizePowerUp extends PowerUp {
    public SizePowerUp(float x, float y, World world){
        super(x, y, world);
        fixture.setUserData(this);
    }

    @Override
    public void powerUpActivate(Player player) {
        // Double the size of the player
        player.getBody().getFixtureList().get(0).getShape().setRadius(player.getBody().getFixtureList().get(0).getShape().getRadius() / 2);

        // Update the player's width and height accordingly
        player.setWidth(player.getWidth() / 2);
        player.setHeight(player.getHeight() / 2);
        activated = true;
        timer = 0f;
    }

    @Override
    public void consume() {
        toDestroy = true;
    }
}

