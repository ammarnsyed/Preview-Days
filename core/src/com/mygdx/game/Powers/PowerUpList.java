package com.mygdx.game.Powers;

import com.badlogic.gdx.physics.box2d.World;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class PowerUpList {
    public ArrayList<String> powerUpClassNames;
    private static PowerUpList instance = null;

    private PowerUpList(){
        powerUpClassNames = new ArrayList<>();

        setUpPowerList();
    }

    public static PowerUpList getInstance(){
        if(instance == null){
            instance = new PowerUpList();
        }
        return instance;
    }
    public ArrayList<String> getPowerUpClassNames(){
        return powerUpClassNames;
    }

    //Start here!

    /**
     * Students will add the names of the classes they create for power ups to this list, making sure to follow the
     * example implementation.
     */
    private void setUpPowerList(){
        powerUpClassNames.add("PowerUpExample");
        powerUpClassNames.add("AntiGravityPowerUp");
        powerUpClassNames.add("SizePowerUp");

    }




}
