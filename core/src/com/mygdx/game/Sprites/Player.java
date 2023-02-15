package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Helper.Constants.PPM;

public class Player extends Entity {

    private int jumpCount;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 9f;
        this.jumpCount = 0;
        fixture.setUserData("Player");
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput(){
        velX = 0;
        //Move Left
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            velX = -1;
        }
        //Move Right
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            velX = 1;
        }
        //Jump
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumpCount < 1){
            float force = body.getMass() * 18;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCount++;
        }
        //Jump is reset
        if(body.getLinearVelocity().y == 0){
            jumpCount = 0;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }
}
