package com.mygdx.game.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Powers.PowerUp;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData() == "Player" || fixB.getUserData() == "Player"){
            Fixture player = fixA.getUserData() == "Player" ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            //See if player colliding w/ a power up to activate the hook
            if(object.getUserData() != null && PowerUp.class.isAssignableFrom(object.getUserData().getClass())){
                ((PowerUp) object.getUserData()).powerUpActivate();
            }

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
