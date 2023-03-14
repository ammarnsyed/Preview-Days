package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Helper.Constants;
import com.mygdx.game.States.MenuState;

import static com.mygdx.game.Helper.Constants.NOTHING_BIT;
import static com.mygdx.game.Helper.Constants.PPM;

public class Player extends Entity {

    public static boolean isDead;
    private int hitCount;
    private int jumpCount;
    private int jumpForce = 18;
    private boolean knockedBack;
    private float knockbackTimer;
    private boolean dead;
    private boolean fallen;
    private boolean needToUpdateBody;

    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    private TextureRegion playerIdle;
    private TextureAtlas atlas = new TextureAtlas("fixedPlayerSpriteSheet.pack");
    private Sprite playerSprite;
    private Animation playerRun;
    private Animation playerJump;
    private boolean isFacingRight;
    private int maxJumps;
    private float stateTimer;
    private World world;

    public Player(float width, float height, Body body, World world) {
        super(width, height, body);
        isDead = false;
        needToUpdateBody = false;
        hitCount = 0;
        this.speed = 9f;
        this.jumpCount = 0;
        this.world = world;
        this.maxJumps = 1;
        fixtureSet();
        knockedBack = false;
        knockbackTimer = 0;
        fallen = false;

        //Animation Logic
        TextureRegion textureRegion = atlas.findRegion("playerSpriteSheet");
        playerIdle = new TextureRegion(textureRegion, 21, 0, 21, 26);
        playerSprite = new Sprite(playerIdle);
        playerSprite.setBounds(0, 0, 64 * width, 64 * height);

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        isFacingRight = true;
        Array<TextureRegion> frames = new Array<>();
        for(int i = 0; i<3; i++){
            frames.add(new TextureRegion(textureRegion, i*21, 0, 21, 26));
        }

        playerRun = new Animation(0.1f, frames);
        frames.clear();
        for(int i = 3; i<4; i++){
            frames.add(new TextureRegion(textureRegion, i*21, 0, 21, 26));
        }
        playerJump = new Animation(0.1f, frames);


    }

    @Override
    public void update(float dt) {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;
        playerSprite.setPosition(x - PPM*width, y - PPM*height);
        playerSprite.setRegion(getFrame(dt));

        knockbackTimer += Gdx.graphics.getDeltaTime();
        if(!knockedBack && knockbackTimer >= 0.5f){
            checkUserInput();
        }

        if(needToUpdateBody){
            changeBody();
        }

    }

    @Override
    public void render(SpriteBatch batch) {
        playerSprite.draw(batch);
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerIdle;
                break;
        }

        if (body.getGravityScale() < 0 && !region.isFlipY()) {
            region.flip(false, true);
        }
        else if (body.getGravityScale() >= 0 && region.isFlipY()) {
            region.flip(false, true);
        }

        if((body.getLinearVelocity().x < 0 || !isFacingRight) && !region.isFlipX()){
            region.flip(true,false);
            isFacingRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || isFacingRight) && region.isFlipX()){
            region.flip(true, false);
            isFacingRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if(body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        }
        else if(body.getLinearVelocity().y < 0){
            return State.FALLING;
        }
        else if(body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else{
            return State.STANDING;
        }
    }

    private void checkUserInput(){
        velX = 0;
        if(!dead && MenuState.isTouched()) {
            //Move Left
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
            }
            //Move Right
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
            }
            //Jump
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCount < maxJumps && maxJumps <= 3 && !fallen) {
                float force = body.getMass() * jumpForce;
                if (body.getGravityScale() < 0) {
                    // If gravity is flipped, apply the jump force in the opposite direction
                    force *= -1;
                }
                body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
                jumpCount++;
            }
            //Jump is reset
            if (body.getLinearVelocity().y == 0 && !fallen) {
                jumpCount = 0;
            }
            body.setLinearVelocity(velX * speed, body.getLinearVelocity().y);
            fallen = body.getLinearVelocity().y < 0;
            }
        else {
            fallen = false;
        }
    }

    public void setJumpForce(int jumpForce){
        this.jumpForce = jumpForce;
    }

    public int getJumpForce(){
        return jumpForce;
    }

    public void playerDeath(){
        Filter filter = new Filter();
        filter.maskBits = NOTHING_BIT;
        dead = true;
        isDead = true;

        for(Fixture fixture : body.getFixtureList()){
            fixture.setFilterData(filter);
        }
    }

    public void playerDamage(NPC npc){
        knockbackTimer = 0;
        Gdx.app.log("Enemy", "Damage");

        float knockbackForce = 20f;
        Vector2 playerVelocity =  npc.body.getPosition().sub(body.getPosition());
        Vector2 knockDirection = new Vector2();
        if(playerVelocity.x > 0){
            knockDirection.x = -1;
        }
        else{
            knockDirection.x = 1;
        }
        knockDirection.y = 1;
        knockDirection.nor();
        Vector2 knockback = knockDirection.scl(knockbackForce);
        body.applyLinearImpulse(knockback, body.getWorldCenter(), true);
    }

    public boolean isDead() {
        return dead;
    }

    public int getMaxJumps() {
        return maxJumps;
    }

    public void setMaxJumps(int maxJumps) {
        this.maxJumps = maxJumps;
    }

    public float getSpeed(){
        return this.speed;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public void setWidth(float width){
        this.width = width;
        playerSprite.setBounds(0, 0, 64 * this.width, 64 * this.height);
        needToUpdateBody = true;

    }
    public float getWidth(){
        return this.width;
    }

    public void setHeight(float height){
        this.height = height;
        playerSprite.setBounds(0, 0, 64 * this.width, 64 * this.height);
        needToUpdateBody = true;

    }
    public float getHeight(){
        return this.height;
    }
    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void changeBody(){
        Runnable destroyBody = new Runnable() {
            @Override
            public void run() {
                body.destroyFixture(fixture);
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(width/2, height);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.friction = 0;
                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
                fixture = body.getFixtureList().get(0);
                fixtureSet();
                shape.dispose();
            }
        };

        Gdx.app.postRunnable(destroyBody);
        needToUpdateBody = false;
    }

    private void fixtureSet(){
        fixture.setUserData(this);
        fixture.getFilterData().categoryBits = Constants.PLAYER_BIT;
        fixture.getFilterData().maskBits =
                Constants.DEFAULT_BIT | Constants.POWER_BIT | Constants.NPC_BIT | Constants.SPIKE_BIT;
    }


    public float getStateTimer() {
        return stateTimer;
    }
}
