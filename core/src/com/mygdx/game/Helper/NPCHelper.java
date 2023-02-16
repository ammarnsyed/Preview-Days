package com.mygdx.game.Helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.Helper.Constants.PPM;

public class NPCHelper {

    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        //Using shape instead of sprite for test
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        EdgeShape rightSide = new EdgeShape();
        rightSide.set(new Vector2( 32 / PPM, -16 / PPM), new Vector2(32 / PPM, 16 / PPM));
        fixtureDef.shape = rightSide;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("Right side");



        return body;
    }
}