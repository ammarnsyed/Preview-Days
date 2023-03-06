package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.Helper.Constants.PPM;

public class NPC extends Entity{

    public enum State {RUNNING};
    public State currentState;
    public State previousState;
    private Sprite npcSprite;
    private TextureAtlas atlas = new TextureAtlas("FixedNPCSprites.pack");
    private Array<TextureRegion> frames;
    private float stateTime;
    private Animation walkAnimation;
    public boolean isFacingRight;

    public NPC(float width, float height, Body body){
        super(width, height, body);
        this.speed = 4f;
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.NPC_BIT;
        fixture.getFilterData().maskBits = Constants.DEFAULT_BIT | Constants.OBSTACLE_BIT | Constants.PLAYER_BIT;
        velX = 1;

        //npc animation
        TextureRegion runTextureRegion = atlas.findRegion("FixedNPCSpriteSheet");
        npcSprite = new Sprite(new TextureRegion(runTextureRegion, 0, 0, 19, 22));
        npcSprite.setBounds(0, 0, 64, 64);
        stateTime = 0;
        isFacingRight = true;
        Array<TextureRegion> frames = new Array<>();
        for(int i = 0; i<2; i++){
            frames.add(new TextureRegion(runTextureRegion, i*19, 0, 19, 22));
        }
        walkAnimation = new Animation(0.4f, frames);

    }

    @Override
    public void update(float delta) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        npcSprite.setPosition(x - PPM, y - PPM);
        npcSprite.setRegion(getFrame(delta));

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }

    @Override
    public void render(SpriteBatch batch) {
        npcSprite.draw(batch);
    }

    public State getState() {
        return State.RUNNING;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            default:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if((body.getLinearVelocity().x < 0 || !isFacingRight) && !region.isFlipX()){
            region.flip(true,false);
            isFacingRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || isFacingRight) && region.isFlipX()){
            region.flip(true, false);
            isFacingRight = true;
        }

        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

    public void reverseVelocity(){
        velX = -velX;
    }
}
