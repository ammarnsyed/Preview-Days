package com.mygdx.game.GameLogic.Checkpoint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.Helper.Constants;
import com.mygdx.game.GameLogic.SoundEffects;

public class Trophy {
    World world;
    private float x,y;
    private Body body;
    private Sprite trophySprite;
    private Fixture fixture;
    private boolean collected;
    private boolean toDestroy;

    public Trophy(float x, float y, Body body){
        this.body = body;
        this.x = x;
        this.y = y;
        world = body.getWorld();
        collected = false;
        toDestroy = false;
        fixture = body.getFixtureList().get(0);
        fixture.getFilterData().categoryBits = Constants.TROPHY_BIT;
        fixture.setUserData(this);

        TextureRegion trophyTexture = new TextureRegion(new Texture("End.png"));
        trophySprite = new Sprite(trophyTexture);
        trophySprite.setBounds(0, 0, 64, 64);
    }

    public void update(float delta){
        trophySprite.setPosition(x - Constants.PPM, y-Constants.PPM);

        if(toDestroy && !collected){
            world.destroyBody(body);
            collected = true;
        }
    }

    public void render(SpriteBatch batch){
        if(!collected){
            trophySprite.draw(batch);
        }
    }

    public void collectTrophy(){
        if(!collected){
            Gdx.app.log("TROPHY", "COLLECTED");
            Sound endSound = SoundEffects.getCheckpointSE();
            toDestroy = true;
            endSound.play(0.5f);
        }
    }

}
