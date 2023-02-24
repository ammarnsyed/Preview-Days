package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Helper.Constants;

import static com.mygdx.game.Helper.Constants.PPM;

public class NPC extends Entity{

    public NPC(float width, float height, Body body){
        super(width, height, body);
        this.speed = 4f;
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.NPC_BIT;
        fixture.getFilterData().maskBits = Constants.DEFAULT_BIT | Constants.OBSTACLE_BIT | Constants.PLAYER_BIT;
        velX = 1;

    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    public void reverseVelocity(){
        velX = -velX;
    }

}
