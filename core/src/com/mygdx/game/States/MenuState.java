package com.mygdx.game.States;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Screens.PlayScreen;

public class MenuState extends State{
  private Texture background;
  private Texture playBtn;


  public MenuState(gStateManager gsm) {
    super(gsm);
    playBtn = new Texture("char.png");
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
    //sb.draw(background,0,0,960, 640);
    sb.draw(playBtn, 0,0,200, 200);
    sb.end();
  }

  @Override
  public void dispose() {
    playBtn.dispose();

  }
}
