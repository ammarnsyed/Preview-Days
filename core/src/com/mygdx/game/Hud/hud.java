package com.mygdx.game.Hud;

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
import com.mygdx.game.Checkpoint.Checkpoint;
import com.mygdx.game.Helper.BodyHelper;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class hud implements Disposable{

  public Stage stage;
  private Viewport viewport;
  private PlayScreen playscreen;

  private float timer;
  private float score;
  private Image image1;
  private static int playerLives;
  private Table newTable;
  private int lives;

  public hud(SpriteBatch spriteBatch, Player player){

    timer = 200;
    score = 0;
    viewport = new FitViewport(1920,1080,new OrthographicCamera());
    stage = new Stage(viewport,spriteBatch);
    Label sc = new Label("Score: ".concat(String.valueOf(score)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label tm = new Label("Time: ".concat(String.valueOf(timer)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));


    newTable = new Table();
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
    if (player.getPlayerLives == 2) {
      image1 = new Image(new Texture("3hp.png"));
    } else if (player.getPlayerLives == 1) {
      image1 = new Image(new Texture("2hp.png"));
    } else if (player.getPlayerLives == 0) {
      image1 = new Image(new Texture("1hp.png"));
    } else {
      image1 = new Image(new Texture("dead.png"));
    }
    newTable.add(image1);
    stage.addActor(newTable);
    /*stage.draw();*/
  }


  public void update(float dt) {
    timer -= dt;
  }

  public void addScore(float val){
    score += val;
  }
  @Override
  public void dispose() {
    stage.dispose();
  }


}
