package com.mygdx.game.GameLogic.Checkpoint;

import com.mygdx.game.GameLogic.PlayScreen;

public class Spawner {
    private static Spawner spawn = null;
    private static MapCoordinate mapCoordinate;

    private Spawner(PlayScreen playScreen){
        playScreen.savePlayerProgress();
    }

    public Spawner() {
        mapCoordinate = new MapCoordinate(3500, 4880);
    }

    public static Spawner getInstance(){
        if(spawn == null){
            spawn = new Spawner();
        }
        return spawn;
    }

    public void setSpawn(int x, int y){
        mapCoordinate.setCoordinate(x, y);
    }

    public float getSpawnX(){
        return mapCoordinate.getX();
    }

    public float getSpawnY(){
        return mapCoordinate.getY();
    }
}
