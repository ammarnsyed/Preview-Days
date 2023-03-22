package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameLogic.Helper.Constants;

import static com.mygdx.game.GameLogic.Helper.Constants.PPM;

public class NPC extends Entity {

    public enum State {RUNNING};
    private Sprite npcSprite;
    private TextureAtlas atlas = new TextureAtlas("NPCFixedSprites.pack");
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

        frames = new Array<>();
        for(int i = 0; i<4; i++){
            frames.add(new TextureRegion(runTextureRegion, i*20, 0, 19, 22));
        }
        walkAnimation = new Animation(0.1f, frames);

    }

    @Override
    protected void update(float dt) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        npcSprite.setPosition(x - PPM, y - PPM);
        npcSprite.setRegion(getFrame(dt));

        body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
    }

    @Override
    protected void render(SpriteBatch batch) {
        npcSprite.draw(batch);
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

        if((body.getLinearVelocity().x < 0 || !isFacingRight) && !region.isFlipX()){
            region.flip(true,false);
            isFacingRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || isFacingRight) && region.isFlipX()){
            region.flip(true, false);
            isFacingRight = true;
        }

        stateTime += dt;
        return region;
    }

    public void reverseVelocity(){
        velX = -velX;
    }
}
