package com.mygdx.game.GameLogic;

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
  private float score;
  private Image image1;
  private Table newTable;
  private Table newTable1;

  private Label sc;
  private Label tm;
  private Label powerUpTimerLabel;

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
    min = (int) (timer/60);
    sec = (int) (timer%60);
    String timerText = String.format("Time: %02d", min) + ":" + String.format("%02d", sec);
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

  public void addScore(float val){
    score += val;
    sc.setText("Score: ".concat(String.valueOf(score)));
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
