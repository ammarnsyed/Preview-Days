package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundEffects {
   static Sound jumpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-player-jumping-in-a-video-game-2043.wav"));
   static Sound powerUpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-game-warning-quick-notification-267.wav"));
   static Sound hitSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Trampoline.wav"));
   static Sound deathSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Phone_Ring.wav"));


    private static Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/music1.mp3"));
    private static Music lowHpMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/1hpdontdie.wav"));


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

   public static void startMainMusic(){
       mainMusic.setLooping(true);
       mainMusic.setVolume(0.5f);
       mainMusic.play();
   }

   public static void startLowHpMusic(){
       lowHpMusic.setLooping(true);
       lowHpMusic.setVolume(0.5f);
       lowHpMusic.play();
   }

   public static void stopMainMusic(){
       mainMusic.stop();
   }

   public static void stopLowHpMusic(){
       lowHpMusic.stop();
   }

}
