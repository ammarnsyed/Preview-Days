package com.mygdx.game.Powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.Constants;
import com.mygdx.game.GameLogic.Player;
import com.mygdx.game.GameLogic.SoundEffects;

public abstract class PowerUp {
    protected float x, y;
    private float stateTime;
    protected Body body;
    protected Fixture fixture;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected World world;
    protected float timer = 0f;
    protected boolean activated;
    Sprite powerSprite;
    private Animation powerUpBlink;

    Sound powerUpSound = SoundEffects.getPowerUpSE();

    public PowerUp(float x, float y, World world){
        this.world = world;
        body = BodyHelper.createCircularBody(x, y, 1, true, world);
        this.x = x;
        this.y = y;
        stateTime = 0;
        fixture = body.getFixtureList().get(0);
        fixture.getFilterData().categoryBits = Constants.POWER_BIT;
        toDestroy = false;
        destroyed = false;
        activated = false;

        TextureRegion powerTexture1 = new TextureRegion(new Texture("powerUp1.png"));
        TextureRegion powerTexture2 = new TextureRegion(new Texture("powerUp2.png"));
        powerSprite = new Sprite(powerTexture1);
        powerSprite.setBounds(0,0, 64,64);

        Array<TextureRegion> frames = new Array<>();
        frames.add(powerTexture1);
        frames.add(powerTexture2);
        powerUpBlink = new Animation(0.5f, frames);


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
        powerSprite.setRegion(getFrame(delta));
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
                player.setGravityScale(1f);
            }
        }
    }

    private TextureRegion getFrame(float dt){
        TextureRegion region;

        region = (TextureRegion) powerUpBlink.getKeyFrame(stateTime, true);
        stateTime += dt;
        return region;
    }

    public boolean getActive(){
        return activated;
    }

    public void consume() {
        powerUpSound.play(0.5f);
        toDestroy = true;
    }
}

