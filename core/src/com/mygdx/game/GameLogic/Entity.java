package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class Entity {
    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;
    protected Fixture fixture;

    public Entity(float width, float height, Body body){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
        this.body = body;
        fixture = body.getFixtureList().get(0);
    }

    protected abstract void update(float delta);

    protected abstract void render(SpriteBatch batch);

    protected Body getBody(){
        return body;
    }

}
