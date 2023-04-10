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
    private World world;
    private Body body;
    private float x, y;
    private Sprite trophySprite;
    private Fixture fixture;
    private boolean collected;

    public Trophy(float x, float y, World world){
        this.world = world;
        body = BodyHelper.createRectangularBody(x, y, 1, 1, true, world);
        this.x = x;
        this.y = y;
        collected = false;
        fixture = body.getFixtureList().get(0);
        fixture.getFilterData().categoryBits = Constants.TROPHY_BIT;
        fixture.setUserData(this);

        TextureRegion trophyTexture = new TextureRegion(new Texture("End.png"));
        trophySprite = new Sprite(trophyTexture);
        trophySprite.setBounds(0, 0, 64, 64);
    }

    public void update(float delta){
        trophySprite.setPosition(x - Constants.PPM, y-Constants.PPM);
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
            collected = true;
            endSound.play(0.5f);
            world.destroyBody(body);
        }
    }

}
