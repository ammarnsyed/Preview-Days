package com.mygdx.game.GameLogic.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State{

  public PlayState(gStateManager gsm) {
    super(gsm);
  }

  @Override
  protected void handleInput() {

  }

  @Override
  public void update(float dt) {
    handleInput();
  }

  @Override
  public void render(SpriteBatch sb) {

  }

  @Override
  public void dispose() {

  }
}
