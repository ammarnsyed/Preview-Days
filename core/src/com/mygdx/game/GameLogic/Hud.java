package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameLogic.Checkpoint.Spawner;
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
  TextButton.TextButtonStyle textButtonStyle;
  private TextButton button1;
  private TextButton button2;
  private TextButton button3;
  private TextButton buttonReset;



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

    textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    button1 = new TextButton("RESUME",textButtonStyle);
    button2 = new TextButton("SETTING",textButtonStyle);
    button3 = new TextButton("EXIT",textButtonStyle);
    buttonReset = new TextButton("RESTART",textButtonStyle);
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
    button1.getLabel().setFontScale(3f,3f);
    button2.getLabel().setFontScale(3f,3f);
    button3.getLabel().setFontScale(3f,3f);
    buttonReset.getLabel().setFontScale(3f,3f);
    newTableP.add(button1);
    newTableP.row();
    newTableP.add(button2);
    newTableP.row();
    newTableP.add(buttonReset);
    newTableP.row();
    newTableP.add(button3);
    Gdx.input.setInputProcessor(stage);
    stage.addActor(newTableP);

  }

  public void buttonDetect(final PlayScreen playScreen){
    button1.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        button1.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        button1.getLabel().setColor(Color.WHITE);
      }
    });
    button1.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.updateResume();
      }
    });


    button2.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        button2.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        button2.getLabel().setColor(Color.WHITE);
      }
    });
    button2.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.updateResume();
      }
    });


    buttonReset.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        buttonReset.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        buttonReset.getLabel().setColor(Color.WHITE);
      }
    });
    buttonReset.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.setRestart();
      }
    });


    button3.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        button3.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        button3.getLabel().setColor(Color.WHITE);
      }
    });
    button3.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.exitGame();
      }
    });
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
