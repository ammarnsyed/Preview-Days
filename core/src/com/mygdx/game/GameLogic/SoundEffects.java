package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffects {
   static Sound jumpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-player-jumping-in-a-video-game-2043.wav"));
   static Sound powerUpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-game-warning-quick-notification-267.wav"));
   static Sound hitSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Trampoline.wav"));
    static Sound deathSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Phone_Ring.wav"));

   public static Sound getJumpSE(){
       return jumpSE;
   }

   public static Sound getPowerUpSE(){
       return powerUpSE;
   }

   public static Sound getHitSE() {
       return hitSE;
   }

   public static Sound getDeathSE(){
       return deathSE;
   }

}
