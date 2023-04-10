package com.mygdx.game.GameLogic.Checkpoint;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.GameLogic.Helper.Constants;
import com.mygdx.game.GameLogic.PlayScreen;
import com.mygdx.game.GameLogic.SoundEffects;

public class Checkpoint {
    private int x;
    private int y;
    private PlayScreen playScreen;
    private boolean checkpointReached;

    public Checkpoint(int x, int y, Body body, PlayScreen playScreen) {
        Fixture fixture = body.getFixtureList().get(0);
        fixture.setSensor(true);
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.CHECKPOINT_BIT;
        //character was spawning in blocks temp fix for now
        this.x = x-20;
        this.y = y;
        this.playScreen = playScreen;
        checkpointReached = false;
    }

    public void changeSpawn() {
        Spawner.getInstance().setSpawn(x, y);
        playScreen.savePlayerProgress();
        playCheckpointSound();
        checkpointReached = true;
    }

    private void playCheckpointSound(){
        if(!checkpointReached){
            Sound checkpointSound = SoundEffects.getCheckpointSE();
            checkpointSound.play(0.5f);
        }
    }
}


