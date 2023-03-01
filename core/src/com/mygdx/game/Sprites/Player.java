package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.Helper.BodyHelper;

import static com.mygdx.game.Helper.Constants.NOTHING_BIT;
import static com.mygdx.game.Helper.Constants.PPM;

public class Player extends Entity {

    private int jumpCount;
    private int jumpForce = 18;
    private boolean knockedBack;
    private float knockbackTimer;
    private boolean dead;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 9f;
        this.jumpCount = 0;
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.PLAYER_BIT;
        fixture.getFilterData().maskBits =
                Constants.DEFAULT_BIT | Constants.POWER_BIT | Constants.NPC_BIT | Constants.OBSTACLE_BIT;

        knockedBack = false;
        knockbackTimer = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        knockbackTimer += Gdx.graphics.getDeltaTime();
        if(!knockedBack && knockbackTimer >= 0.5f){
            checkUserInput();
        }

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput(){
        velX = 0;
        if(!dead) {
            //Move Left
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
            }
            //Move Right
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
            }
            //Jump
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumpCount < 1) {
                float force = body.getMass() * jumpForce;
                body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
                jumpCount++;
            }
            //Jump is reset
            if (body.getLinearVelocity().y == 0) {
                jumpCount = 0;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
        }
    }

    public void setJumpForce(int jumpForce){
        this.jumpForce = jumpForce;
    }

    public int getJumpForce(){
        return jumpForce;
    }

    public void playerDeath(){
        Filter filter = new Filter();
        filter.maskBits = NOTHING_BIT;
        dead = true;

        for(Fixture fixture : body.getFixtureList()){
            fixture.setFilterData(filter);
        }
    }

    public void playerDamage(NPC npc){
        knockbackTimer = 0;
        Gdx.app.log("Enemy", "Damage");

        float knockbackForce = 20f;
        Vector2 playerVelocity =  npc.body.getPosition().sub(body.getPosition());
        Vector2 knockDirection = new Vector2();
        if(playerVelocity.x > 0){
            knockDirection.x = -1;
        }
        else{
            knockDirection.x = 1;
        }
        knockDirection.y = 1;
        knockDirection.nor();
        Vector2 knockback = knockDirection.scl(knockbackForce);
        body.applyLinearImpulse(knockback, body.getWorldCenter(), true);
    }

    public boolean isDead() {
        return dead;
    }

    public float getSpeed(){
        return this.speed;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public void setWidth(float width){
        this.width = width;
    }
    public float getWidth(){
        return this.width;
    }

    public void setHeight(float height){
        this.height = height;
    }
    public float getHeight(){
        return this.height;
    }
}
