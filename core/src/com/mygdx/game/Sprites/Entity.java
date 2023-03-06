package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Circle;
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

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public Body getBody(){
        return body;
    }

}
