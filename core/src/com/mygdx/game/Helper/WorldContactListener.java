package com.mygdx.game.Helper;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Powers.PowerUp;
import com.mygdx.game.Sprites.NPC;
import com.mygdx.game.Sprites.Player;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Constants.PLAYER_BIT | Constants.NPC_BIT:
                if(fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    ((Player)fixA.getUserData()).playerDeath();
                    ((Player)fixA.getUserData()).playerDamage((NPC)fixB.getUserData());
                }
                else{
                    ((Player)fixB.getUserData()).playerDeath();
                    ((Player)fixB.getUserData()).playerDamage((NPC)fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.POWER_BIT:
                if(fixA.getFilterData().categoryBits == Constants.POWER_BIT){
                    ((PowerUp)fixA.getUserData()).powerUpActivate((Player)fixB.getUserData());
                    ((PowerUp)fixA.getUserData()).consume();
                }
                else{
                    ((PowerUp)fixB.getUserData()).powerUpActivate((Player)fixA.getUserData());
                    ((PowerUp)fixB.getUserData()).consume();
                }

                break;
            case Constants.NPC_BIT | Constants.OBSTACLE_BIT:
                if(fixA.getFilterData().categoryBits == Constants.NPC_BIT){
                    ((NPC)fixA.getUserData()).reverseVelocity();
                }
                else{
                    ((NPC)fixB.getUserData()).reverseVelocity();
                }
                break;
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
