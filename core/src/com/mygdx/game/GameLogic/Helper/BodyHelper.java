package com.mygdx.game.GameLogic.Helper;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.GameLogic.Helper.Constants.PPM;

public class BodyHelper {

    public static Body createRectangularBody(float x, float y, float width, float height, boolean isStatic, World world){

        Body body = bodyCreator(x, y, isStatic, world);

        //Using shape instead of sprite for test
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public static Body createCircularBody(float x, float y, float radius, boolean isStatic, World world){
        Body body = bodyCreator(x, y, isStatic, world);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    private static Body bodyCreator(float x, float y, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        return world.createBody(bodyDef);
    }

}
