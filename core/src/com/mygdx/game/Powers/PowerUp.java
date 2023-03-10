package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
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

    public PowerUp(float x, float y, World world){
        this.world = world;
        body = BodyHelper.createBody(x, y, 1, 1, true, world);
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        fixture = body.getFixtureList().get(0);
        fixture.getFilterData().categoryBits = Constants.POWER_BIT;
        toDestroy = false;
        destroyed = false;
        activated = false;
    }

    public abstract void powerUpActivate(Player player);

    public Body getBody(){
        return body;
    }
    public void setActivated(float time, boolean active){
        timer = time;
        activated = active;
    }

    public void update(Player player, float delta){
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
            }
        }
    }

    public void consume() {
        toDestroy = true;
    }
}

