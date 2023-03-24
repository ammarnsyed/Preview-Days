package com.mygdx.game.GameLogic;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Checkpoint.MapCoordinate;
import com.mygdx.game.Powers.PowerUp;
import com.mygdx.game.Powers.PowerUpList;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class PowerUpHelper {
    private ArrayList<String> powerUpClassNames;
    private ArrayList<MapCoordinate> powerUpLocations;
    private World world;

    public ArrayList<PowerUp> powerUps;

    public PowerUpHelper(ArrayList<MapCoordinate> powerLocations, World world){
        powerUpClassNames = PowerUpList.getInstance().getPowerUpClassNames();
        powerUpLocations = powerLocations;
        powerUps = new ArrayList<>();
        this.world = world;
        getPowerUpsFromStudent();
    }

    public void getPowerUpsFromStudent(){
        Class<? extends PowerUp>[] powerupClasses = new Class[powerUpClassNames.size()];

        for(int i = 0; i<powerUpClassNames.size(); i++){
            String className = powerUpClassNames.get(i);
            String fullClassName = "com.mygdx.game.Powers." + className;

            try{
                Class<? extends PowerUp> powerupClass = Class.forName(fullClassName).asSubclass(PowerUp.class);
                powerupClasses[i] = powerupClass;
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }


        for(int i = 0; i<powerUpLocations.size(); i++){
            Class<? extends  PowerUp> powerupClass;
            if(i < powerupClasses.length){
                powerupClass = powerupClasses[i];
            }
            else{
                powerupClass = PowerUp.class;
            }

            try{
                Constructor<? extends PowerUp> constructor = powerupClass.getDeclaredConstructor(float.class, float.class, World.class);
                powerUps.add(constructor.newInstance(powerUpLocations.get(i).getX(), powerUpLocations.get(i).getY(), world));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }
}
