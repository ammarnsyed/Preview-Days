package com.mygdx.game.GameLogic;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class LeaderBoard extends ScreenAdapter{
  private Stage stage;
  private Table table;

  public LeaderBoard(){

  }

  @Override
  public void show() {
    stage = new Stage();
    table = new Table();
    table.setFillParent(true);
    stage.addActor(table);



    Label rankLabel = new Label("Rank", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label nameLabel = new Label("Name", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label scoreLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label timeLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
    Label title = new Label("Leaderboard", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    rankLabel.setFontScale(3f,3f);
    nameLabel.setFontScale(3f,3f);
    scoreLabel.setFontScale(3f,3f);
    timeLabel.setFontScale(3f,3f);
    title.setFontScale(5f,5f);
    table.padTop(-50);
    table.add(title).colspan(4).row();
    table.add(rankLabel).padRight(50);
    table.add(nameLabel).padRight(50);
    table.add(timeLabel).padRight(50);
    table.add(scoreLabel).padRight(50).row();

    // TODO: Retrieve the scores from the server and add them to the table
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    stage.dispose();
  }
}
