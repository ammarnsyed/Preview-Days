package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameLogic.Checkpoint.Checkpoint;
import com.mygdx.game.GameLogic.Helper.BodyHelper;
import com.mygdx.game.GameLogic.PlayScreen;
import com.mygdx.game.GameLogic.Player;

public class Hud implements Disposable{

  public Stage stage;
  private Viewport viewport;
  private PlayScreen playscreen;

  private float timer;
  private float score;
  private Image image1;
  private Table newTable;
  private Table newTable1;

  public Hud(SpriteBatch spriteBatch, Player player){

    timer = 200;
    score = 0;
    viewport = new FitViewport(1920,1080,new OrthographicCamera());
    stage = new Stage(viewport,spriteBatch);
    Label sc = new Label("Score: ".concat(String.valueOf(score)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label tm = new Label("Time: ".concat(String.valueOf(timer)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));


    newTable = new Table();
    newTable1 = new Table();
    newTable1.setFillParent(true);
    newTable.setFillParent(true);
    sc.setFontScale(3f,3f);
    tm.setFontScale(3f,3f);
    newTable.top();
    newTable.left();
    newTable.padLeft(20f);
    newTable.row();
    newTable.add(sc);
    newTable.row();
    newTable.add(tm);
    newTable.row();
    newTable1.top();
    newTable1.right();
    newTable.padRight(20f);
    updateLives(player.getLives());
    stage.addActor(newTable);
    stage.addActor(newTable1);
  }


  public void update(float dt, Player player) {
    timer -= dt;
    updateLives(player.getLives());
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
  }
  @Override
  public void dispose() {
    stage.dispose();
  }


}
