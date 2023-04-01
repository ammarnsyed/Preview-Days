package com.mygdx.game.GameLogic.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameLogic.SoundEffects;

public class MenuState extends State{
  private static boolean isTouched;
  private Texture playBtn;
  private Texture charImg;
  private Texture bgImg;


  public MenuState(gStateManager gsm) {
    super(gsm);
    bgImg = new Texture("bgImg.png");
    playBtn = new Texture("startButton.png");
    charImg = new Texture("char.png");;
  }

  @Override
  public void handleInput() {
    if(Gdx.input.justTouched()){
      Sound startSound = SoundEffects.getUISE();
      startSound.play(0.5f);
      gsm.set(new PlayState(gsm));
      isTouched = true;
    }
  }

  @Override
  public void update(float dt) {
    handleInput();
  }

  @Override
  public void render(SpriteBatch sb) {
    if(!isTouched()) {
      sb.begin();
      sb.draw(bgImg, 2850, 3850, 3920, 3080);
      sb.draw(charImg, 3250, 4630, 170, 170);
      sb.draw(playBtn, 3150, 4430, 351, 150);
      sb.end();
    }
  }

  @Override
  public void dispose() {
    playBtn.dispose();

  }

  public static boolean isTouched() {
    return isTouched;
  }

}
