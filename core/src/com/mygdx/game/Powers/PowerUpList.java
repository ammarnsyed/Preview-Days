package com.mygdx.game.Powers;

import java.util.ArrayList;

public class PowerUpList {
    public ArrayList<PowerUp> powerUpArrayList;
    private static PowerUpList instance = null;

    private PowerUpList(){
        powerUpArrayList = new ArrayList<>();
    }

    public static PowerUpList getInstance(){
        if(instance == null){
            instance = new PowerUpList();
        }
        return instance;
    }

//    private ArrayList<PowerUp> PowerUpListMaker(){
//        PowerUpExample powerUp1;
//        PowerUpExample powerUp2;
//        PowerUpExample powerUp3;
//        PowerUpExample powerUp4;
//
//    }

}
