package com.mygdx.game.GameLogic;

import com.mygdx.game.GameLogic.Helper.Constants;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameLogic.Checkpoint.Checkpoint;
import com.mygdx.game.Powers.PowerUp;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case Constants.PLAYER_BIT | Constants.NPC_BIT:
                if(fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    ((Player)fixA.getUserData()).playerCheckToDie();
                    ((Player)fixA.getUserData()).playerDamage((NPC)fixB.getUserData());
                }
                else{
                    ((Player)fixB.getUserData()).playerDamage((NPC)fixA.getUserData());
                }
                break;
            case Constants.PLAYER_BIT | Constants.SPIKE_BIT:
                if(fixA.getFilterData().categoryBits == Constants.PLAYER_BIT){
                    ((Player)fixA.getUserData()).playerDeath();
                }
                else{
                    ((Player)fixB.getUserData()).playerDeath();
                }
                break;
            case Constants.PLAYER_BIT | Constants.POWER_BIT:
                if(fixA.getFilterData().categoryBits == Constants.POWER_BIT && !((PowerUp)fixA.getUserData()).getActive()){
                    ((PowerUp)fixA.getUserData()).powerUpActivate((Player)fixB.getUserData());
                    ((PowerUp)fixA.getUserData()).consume();
                    ((PowerUp)fixA.getUserData()).setActivated(0f, true);
                }
                else if(!((PowerUp)fixB.getUserData()).getActive()){
                    ((PowerUp)fixB.getUserData()).powerUpActivate((Player)fixA.getUserData());
                    ((PowerUp)fixB.getUserData()).consume();
                    ((PowerUp)fixB.getUserData()).setActivated(0f, true);
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
            case Constants.PLAYER_BIT | Constants.CHECKPOINT_BIT:
                if(fixA.getFilterData().categoryBits == Constants.CHECKPOINT_BIT){
                    ((Checkpoint)fixA.getUserData()).printContact();
                    ((Checkpoint)fixA.getUserData()).getCheckName();
                }
                else{
                    ((Checkpoint)fixB.getUserData()).printContact();
                    ((Checkpoint)fixB.getUserData()).getCheckName();
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
