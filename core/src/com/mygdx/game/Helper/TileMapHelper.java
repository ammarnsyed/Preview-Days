package com.mygdx.game.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.NPC;

import static com.mygdx.game.Helper.Constants.PPM;

public class TileMapHelper {
    private TiledMap tiledMap;
    private PlayScreen playScreen;
    public Array<NPC> NPCs;

    public TileMapHelper(PlayScreen playScreen){
        this.playScreen = playScreen;
        NPCs = new Array<>();
    }

    public Array<NPC> getNPCs() {
        return NPCs;
    }

    public OrthogonalTiledMapRenderer mapSetup(){
        tiledMap = new TmxMapLoader().load("MapLayout.tmx");

        for(int i = 2; i<=5; i++){
            parseMapObjects(tiledMap.getLayers().get(i).getObjects(), i-1);
        }
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects, int layer){
        for(MapObject mapObject : mapObjects){

            if(mapObject instanceof PolygonMapObject){
                if(layer == 4){
                    createNPC((PolygonMapObject) mapObject);
                }
                else{
                    createStaticBody((PolygonMapObject) mapObject, layer);
                }

            }
        }
    }

    private void createNPC(PolygonMapObject polygonMapObject){
        Gdx.app.log("npc position", polygonMapObject.getPolygon().getX() + " " + polygonMapObject.getPolygon().getY());
        Body npcBody = BodyHelper.createRectangularBody(polygonMapObject.getPolygon().getX(), polygonMapObject.getPolygon().getY(), 0.5f, 1, false, playScreen.getWorld());
        NPCs.add(new NPC(1, 1, npcBody));
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

