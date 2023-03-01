package com.mygdx.game.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Screens.PlayScreen;

public class MenuState extends State{
  private Texture playBtn;
  private Texture charImg;


  public MenuState(gStateManager gsm) {
    super(gsm);
    playBtn = new Texture("startButton.png");
    charImg = new Texture("char.png");
  }

  @Override
  public void handleInput() {
    if(Gdx.input.justTouched()){
      gsm.set(new PlayState(gsm));
    }
  }

  @Override
  public void update(float dt) {
    handleInput();
  }

  @Override
  public void render(SpriteBatch sb) {
    sb.begin();
    sb.draw(charImg,-30,200, 170,170);
    sb.draw(playBtn, -120,0,351, 150);
    sb.end();
  }

  @Override
  public void dispose() {
    playBtn.dispose();

  }
}
