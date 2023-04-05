package com.mygdx.game.GameLogic.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameLogic.SoundEffects;

public class MenuState extends State{
  private static boolean isTouched;

  //defining images being used on main menu
  private Texture playBtn;
  private Texture bgImg;
  private  Texture instructions;


  public MenuState(gStateManager gsm) {
    super(gsm);
    //path to where the images are located
    bgImg = new Texture("bgImg.png");
    playBtn = new Texture("startButton.png");
    instructions = new Texture("instructions.png");
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

  // these are the coordinates for images on the start screen
  @Override
  public void render(SpriteBatch sb) {
    if(!isTouched()) {
      sb.begin();
      sb.draw(bgImg, 2540, 4227, 1920, 1080);
      sb.draw(playBtn, 3325, 4780, 351, 150);
      sb.draw(instructions,3325, 4500, 351, 150);
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
