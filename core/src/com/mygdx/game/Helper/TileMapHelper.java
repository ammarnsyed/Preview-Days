package com.mygdx.game.Helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.Helper.Constants.PPM;

public class TileMapHelper {
    private TiledMap tiledMap;
    private PlayScreen playScreen;

    public TileMapHelper(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public OrthogonalTiledMapRenderer mapSetup(){
        tiledMap = new TmxMapLoader().load("MapLayout.tmx");
        parseMapObjects(tiledMap.getLayers().get(2).getObjects(), 1);
        parseMapObjects(tiledMap.getLayers().get(3).getObjects(), 2);
        parseMapObjects(tiledMap.getLayers().get(4).getObjects(), 3);

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects, int layer){
        for(MapObject mapObject : mapObjects){

            if(mapObject instanceof PolygonMapObject){
                createStaticBody((PolygonMapObject) mapObject, layer);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject, int layer){
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = playScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        switch(layer){
            case 1:
                fixture.getFilterData().categoryBits = Constants.DEFAULT_BIT;
                break;
            case 2:
                fixture.getFilterData().categoryBits = Constants.OBSTACLE_BIT;
                break;
            case 3:
                fixture.getFilterData().categoryBits = Constants.SPIKE_BIT;
        }

        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i = 0; i < vertices.length / 2; i++){
            Vector2 current = new Vector2(vertices[i*2]/PPM, vertices[i*2+1]/PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
