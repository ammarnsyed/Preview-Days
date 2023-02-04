package com.mygdx.game.Helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {
    private TiledMap tiledMap;

    public TileMapHelper(){

    }

    public OrthogonalTiledMapRenderer mapSetup(){
        tiledMap = new TmxMapLoader().load("TestMapTiled.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }
}
