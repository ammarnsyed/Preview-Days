package com.mygdx.game.Powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.spine.BlendMode;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.Sprites.Player;

public abstract class PowerUp {
    protected float x, y;
    protected Body body;
    protected Fixture fixture;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected World world;
    protected float timer = 0f;
    protected boolean activated;
    Sprite powerSprite;
    ShapeRenderer shapeRenderer;

    public PowerUp(float x, float y, World world){
        this.world = world;
        body = BodyHelper.createBody(x, y, 1, 1, true, world);
        this.x = x;
        this.y = y;
        fixture = body.getFixtureList().get(0);
        fixture.getFilterData().categoryBits = Constants.POWER_BIT;
        toDestroy = false;
        destroyed = false;
        activated = false;

        TextureRegion powerTexture = new TextureRegion(new Texture("powerUpImg1.png"));
        powerSprite = new Sprite(powerTexture);
        powerSprite.setBounds(0,0, 64,64);
        shapeRenderer = new ShapeRenderer();
    }

    public abstract void powerUpActivate(Player player);

    public void render(SpriteBatch batch){
        if(!destroyed){
            powerSprite.draw(batch);
        }

    }

    public Body getBody(){
        return body;
    }
    public void setActivated(float time, boolean active){
        timer = time;
        activated = active;
    }

    public void update(Player player, float delta){
        powerSprite.setPosition(x - Constants.PPM, y - Constants.PPM);
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
        if (activated) {
            timer += delta;
            if (timer > 10f) { // Change 10f to the desired duration of the powerup
                timer = 0f;
                activated = false;
                player.setJumpForce(Constants.PLAYER_JUMP_FORCE);
                player.setSpeed(Constants.PLAYER_SPEED);
                player.setWidth(Constants.PLAYER_WIDTH);
                player.setHeight(Constants.PLAYER_HEIGHT);
                player.setMaxJumps(Constants.PLAYER_MAX_JUMPS);
                player.getBody().setGravityScale(1f);
            }
        }
    }

    public boolean getActive(){
        return activated;
    }

    public void consume() {
        toDestroy = true;
    }
}

