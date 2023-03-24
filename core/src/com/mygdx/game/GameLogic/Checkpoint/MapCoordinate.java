package com.mygdx.game.GameLogic.Checkpoint;

public class MapCoordinate {
    float x;
    float y;
    public MapCoordinate(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
