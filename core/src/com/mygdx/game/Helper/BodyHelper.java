package com.mygdx.game.Helper;

import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.Helper.Constants.PPM;

public class BodyHelper {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        //Using shape instead of sprite for test
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 /PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }
}
