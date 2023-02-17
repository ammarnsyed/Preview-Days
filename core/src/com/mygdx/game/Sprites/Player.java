package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.Helper.BodyHelper;

import static com.mygdx.game.Helper.Constants.PPM;

public class Player extends Entity {

    private int jumpCount;
    private int jumpForce = 18;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 9f;
        this.health = 100;
        this.jumpCount = 0;
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.PLAYER_BIT;
        fixture.getFilterData().maskBits =
                Constants.DEFAULT_BIT | Constants.POWER_BIT | Constants.NPC_BIT | Constants.OBSTACLE_BIT;
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
            float force = body.getMass() * jumpForce;
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumpCount++;
        }
        //Jump is reset
        if(body.getLinearVelocity().y == 0){
            jumpCount = 0;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }

    public void setJumpForce(int jumpForce){
        this.jumpForce = jumpForce;
    }

    public int getJumpForce(){
        return jumpForce;
    }

    public void playerDeath(){
        if(health <= 0){
            Gdx.app.log("Enemy", "Death");
            health = 0;
        }
    }

    public void playerDamage(){
        health = health - 20;
        Gdx.app.log("Enemy", "Damage");
        Gdx.app.log("Damage", String.valueOf(health));
        if(jumpCount < 1){
            float force = body.getMass() * jumpForce;
            body.applyLinearImpulse(new Vector2(0, force / 2), body.getPosition(), true);
            jumpCount++;
        }

        if(body.getLinearVelocity().y == 0){
            jumpCount = 0;
        }
        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }
}
