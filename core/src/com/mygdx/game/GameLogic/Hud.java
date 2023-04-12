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
  private Image imgHeart;
  private Table scoreTable;
  private Table lifeTable;

  private Label sc;
  private Label tm;
  private Label powerUpTimerLabel;

  private Table pauseTable;
  TextButton.TextButtonStyle textButtonStyle;
  private TextButton buttonResume;
  private TextButton buttonSetting;
  private TextButton buttonExit;
  private TextButton buttonReset;



  public Hud(SpriteBatch spriteBatch, Player player){
    timer = 0;
    score = 0;
    viewport = new FitViewport(1920,1080,new OrthographicCamera());
    stage = new Stage(viewport,spriteBatch);
    sc = new Label("Score: ".concat(String.valueOf(score)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    tm = new Label("Time: ".concat(String.valueOf(timer)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    powerUpTimerLabel = new Label("Powerup: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    scoreTable = new Table();
    lifeTable = new Table();
    lifeTable.setFillParent(true);
    scoreTable.setFillParent(true);
    sc.setFontScale(3f,3f);
    tm.setFontScale(3f,3f);
    powerUpTimerLabel.setFontScale(3f, 3f);
    scoreTable.top();
    scoreTable.left();
    scoreTable.add(sc);
    scoreTable.row();
    scoreTable.add(tm);
    scoreTable.row();
    scoreTable.add(powerUpTimerLabel);
    lifeTable.top();
    lifeTable.right();
    updateLives(player.getLives());
    stage.addActor(scoreTable);
    stage.addActor(lifeTable);


    textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.font = new BitmapFont();
    buttonResume = new TextButton("RESUME",textButtonStyle);
    buttonSetting = new TextButton("SETTING",textButtonStyle);
    buttonExit = new TextButton("EXIT",textButtonStyle);
    buttonReset = new TextButton("RESTART",textButtonStyle);
    pauseTable = new Table();
    pauseTable.setFillParent(true);
    buttonResume.getLabel().setFontScale(3f,3f);
    buttonSetting.getLabel().setFontScale(3f,3f);
    buttonExit.getLabel().setFontScale(3f,3f);
    buttonReset.getLabel().setFontScale(3f,3f);
    pauseTable.add(buttonResume);
    pauseTable.row();
    pauseTable.add(buttonSetting);
    pauseTable.row();
    pauseTable.add(buttonReset);
    pauseTable.row();
    pauseTable.add(buttonExit);
    Gdx.input.setInputProcessor(stage);
  }

  //updates score and timer during playtime
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

  //updates lives images
  public void updateLives(int playerLives) {
    lifeTable.removeActor(imgHeart);
    if (playerLives == 2) {
      imgHeart = new Image(new Texture("3hp.png"));
    } else if (playerLives == 1) {
      imgHeart = new Image(new Texture("2hp.png"));
    } else if (playerLives == 0) {
      imgHeart = new Image(new Texture("1hp.png"));
    } else {
      imgHeart = new Image(new Texture("dead.png"));
    }
    lifeTable.add(imgHeart);
  }

  //popping pause menu on screen
  protected void updatePause(){
    stage.addActor(pauseTable);
  }

  //give pause menu buttons functionality
  public void buttonDetect(final PlayScreen playScreen){
    buttonResume.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        buttonResume.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        buttonResume.getLabel().setColor(Color.WHITE);
      }
    });
    buttonResume.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.updateResume();
        Sound resumeSE = SoundEffects.getResumeSE();
        SoundEffects.changeMainMusicVolume(0.25f);
        resumeSE.play(0.25f);
      }
    });


    //setting button is a placeholder, it functions as resume button rn
    buttonSetting.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        buttonSetting.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        buttonSetting.getLabel().setColor(Color.WHITE);
      }
    });
    buttonSetting.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.updateResume();
        Sound resumeSE = SoundEffects.getResumeSE();
        SoundEffects.changeMainMusicVolume(0.25f);
        resumeSE.play(0.25f);
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


    buttonExit.addListener(new ClickListener() {
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        super.enter(event, x, y, pointer, fromActor);
        buttonExit.getLabel().setColor(Color.GRAY);
      }
      @Override
      public void exit(InputEvent event, float x, float y, int pointer, @Null Actor toActor) {
        buttonExit.getLabel().setColor(Color.WHITE);
      }
    });
    buttonExit.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        playScreen.exitGame();
      }
    });
  }

  //remove pause menu
  public void updateResume(){
    pauseTable.remove();
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
