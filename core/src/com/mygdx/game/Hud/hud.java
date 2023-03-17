package com.mygdx.game.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class hud implements Disposable{
  public Stage stage;
  private Viewport viewport;

  private float timer;
  private float score;

  public hud(SpriteBatch spriteBatch){
    timer = 200;
    score = 0;

    viewport = new FitViewport(1920,1080,new OrthographicCamera());
    stage = new Stage(viewport,spriteBatch);

    Label sc = new Label("Score: ".concat(String.valueOf(score)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label tm = new Label("Time: ".concat(String.valueOf(timer)),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    Table newTable = new Table();
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

    stage.addActor(newTable);
    /*stage.draw();*/
  }
  public void update(float dt){
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
