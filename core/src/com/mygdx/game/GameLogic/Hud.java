package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Powers.PowerUp;

import java.util.ArrayList;

public class Hud implements Disposable{

  public Stage stage;
  private Viewport viewport;

  private int min;
  private int sec;
  private float timer;
  private int score;
  private Image image1;
  private Table newTable;
  private Table newTable1;

  private Label sc;
  private Label tm;
  private Label powerUpTimerLabel;

  private Table newTableP;
  private Label button1 = new Label("RESUME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
  private Label button2 = new Label("SETTING",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
  private Label button3 = new Label("EXIT",new Label.LabelStyle(new BitmapFont(), Color.WHITE));


  public Hud(SpriteBatch spriteBatch, Player player){
    timer = 0;
    score = 0;
    viewport = new FitViewport(1920,1080,new OrthographicCamera());
    stage = new Stage(viewport,spriteBatch);
    sc = new Label("Score: ".concat(String.valueOf(score)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    tm = new Label("Time: ".concat(String.valueOf(timer)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    powerUpTimerLabel = new Label("Powerup: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    newTable = new Table();
    newTable1 = new Table();
    newTable1.setFillParent(true);
    newTable.setFillParent(true);
    sc.setFontScale(3f,3f);
    tm.setFontScale(3f,3f);
    powerUpTimerLabel.setFontScale(3f, 3f);
    newTable.top();
    newTable.left();
    newTable.add(sc);
    newTable.row();
    newTable.add(tm);
    newTable.row();
    newTable.add(powerUpTimerLabel);
    newTable1.top();
    newTable1.right();
    updateLives(player.getLives());
    stage.addActor(newTable);
    stage.addActor(newTable1);
  }

  public void update(float dt, Player player, ArrayList<PowerUp> actualPowerUps) {
    timer += dt;
    //format the timer to min:sec
    min = (int) (timer/60);
    sec = (int) (timer%60);
    String timerText = String.format("Time: %02d", min) + ":" + String.format("%02d", sec);
    //every minute the user can live, 60 points score will be added
    if(sec==0){
      score += 1;
    }
    String scoreText = String.format("Score: %d", score);
    sc.setText(scoreText);
    tm.setText(timerText);

    updateLives(player.getLives());

    boolean powerUpActive = false;
    float powerUpTimer = 0;
    for (PowerUp powerUp : actualPowerUps) {
      if (powerUp.getActive()) {
        powerUpActive = true;
        powerUpTimer = powerUp.getDuration();
        break;
      }
    }

    if (powerUpActive) {
      powerUpTimerLabel.setText(String.format("Powerup: %.1fs", powerUpTimer));
      powerUpTimerLabel.setVisible(true);
    } else {
      powerUpTimerLabel.setVisible(false);
    }
  }

  protected void updatePause(){
    newTableP = new Table();
    newTableP.setFillParent(true);
    button1.setFontScale(3f,3f);
    button2.setFontScale(3f,3f);
    button3.setFontScale(3f,3f);
    newTableP.add(button1);
    newTableP.row();
    newTableP.add(button2);
    newTableP.row();
    newTableP.add(button3);
    newTableP.center();
    stage.addActor(newTableP);

  }

  public void buttonDetect(PlayScreen playScreen){
    float x1 = button1.getX(), x2 = button2.getX(), x3 = button3.getX();
    float y1 = button1.getY(), y2 = button2.getY(), y3 = button3.getY();
    float w1 = button1.getWidth(), w2 = button2.getWidth(), w3 = button3.getWidth();
    float h1 = button1.getHeight(), h2 = button2.getHeight(), h3 = button3.getHeight();
    if(Gdx.input.getX() < x1 + 2 * button1.getWidth() && Gdx.input.getX() > x1 + 0.75 * button1.getWidth() && Gdx.input.getY() < button1.getY() + 2.5 * button1.getHeight() && Gdx.input.getY() > button1.getY() + 1.5 * button1.getHeight() ){
      button1.setColor(Color.GRAY);
      if(Gdx.input.isTouched()){
        playScreen.updateResume();
      }
    }else{
      button1.setColor(Color.WHITE);
    }
    if(Gdx.input.getX() < button2.getX() + 1.8 * button2.getWidth() && Gdx.input.getX() > button2.getX() + 0.6 * button2.getWidth() && Gdx.input.getY() < button2.getY() + 4.7 * button2.getHeight() && Gdx.input.getY() > button2.getY() + 3.9 * button2.getHeight() ){
      button2.setColor(Color.GRAY);
      if(Gdx.input.isTouched()){
        playScreen.updateResume();
      }
    }else{
      button2.setColor(Color.WHITE);
    }
    if(Gdx.input.getX() < button3.getX() + 3 * button3.getWidth() && Gdx.input.getX() > button3.getX() + 1.7 * button3.getWidth() && Gdx.input.getY() < button3.getY() + 7 * button3.getHeight() && Gdx.input.getY() > button3.getY() + 6 * button3.getHeight() ){
      button3.setColor(Color.GRAY);
      if(Gdx.input.isTouched()){
        playScreen.exitGame();
      }
    }else{
      button3.setColor(Color.WHITE);
    }
  }

  public void updateResume(){
    newTableP.remove();
  }

  public void updateLives(int playerLives) {
    newTable1.removeActor(image1);
    if (playerLives == 2) {
      image1 = new Image(new Texture("3hp.png"));
    } else if (playerLives == 1) {
      image1 = new Image(new Texture("2hp.png"));
    } else if (playerLives == 0) {
      image1 = new Image(new Texture("1hp.png"));
    } else {
      image1 = new Image(new Texture("dead.png"));
    }
    newTable1.add(image1);
  }


  public float getTime() {
    return timer;
  }

  public void setTime(float timer) {
    this.timer = timer;
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

}
