package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.Helper.Constants.PPM;

public class NPC extends Entity{

    public NPC(float width, float height, Body body){
        super(width, height, body);
        this.speed = 2f;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        enemyMovement();
    }

    @Override
    public void render(SpriteBatch batch) {

    }


    public void enemyMovement(){

    }
}
