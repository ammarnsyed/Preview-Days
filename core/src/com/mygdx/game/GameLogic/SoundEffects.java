package com.mygdx.game.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;

public class SoundEffects {
   static Sound jumpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-player-jumping-in-a-video-game-2043.wav"));
   static Sound powerUpSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/mixkit-game-warning-quick-notification-267.wav"));
   static Sound hitSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Trampoline.wav"));
   static Sound deathSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/Phone_Ring.wav"));
   static Sound UISE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/SFX_UI_Confirm.wav"));
   static Sound pauseSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/SFX_UI_Pause.wav"));
   static Sound resumeSE = Gdx.audio.newSound(Gdx.files.internal("Sound/Effects/SFX_UI_Resume.wav"));


    private static Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/music1.mp3"));
    private static Music lowHpMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/1hpdontdie.wav"));
    private static Music powerUpMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/powerupmusic.mp3"));
    private static Music currentMusic;

   public static Sound getJumpSE(){
       return jumpSE;
   }

   public static Sound getPauseSE(){return pauseSE;}

    public static Sound getResumeSE(){return resumeSE;}

   public static Sound getPowerUpSE(){
       return powerUpSE;
   }

   public static Sound getHitSE() {
       return hitSE;
   }

   public static Sound getDeathSE(){
       return deathSE;
   }

   public static Sound getUISE(){return UISE;}

    public static Timer.Task fadeOutPowerTask = new Timer.Task() {
        @Override
        public void run() {

        }
    };

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

   public static void startPowerUpMusic(){
       powerUpMusic.setLooping(true);
       powerUpMusic.setVolume(0.25f);
       powerUpMusic.play();
   }

   public static void stopCurrentMusic(){
       saveCurrentMusic();
       if(lowHpMusic.isPlaying()){
           lowHpMusic.stop();
       }
       if(mainMusic.isPlaying()){
           mainMusic.stop();
       }
       if(powerUpMusic.isPlaying()){
           powerUpMusic.stop();
       }
   }

   public static void startCurrentMusic(){
       if(currentMusic.equals(mainMusic)){
           startMainMusic();
       }
       else{
           startLowHpMusic();
       }
   }

   private static Music saveCurrentMusic(){
       if(mainMusic.isPlaying()){
           currentMusic = mainMusic;
       }
       else{
           currentMusic = lowHpMusic;
       }

       return currentMusic;
   }

   public static void fadePowerUpMusic(float duration){
       float initialVol = powerUpMusic.getVolume();
       final float volumeChange = initialVol/duration;

       fadeOutPowerTask = Timer.schedule(new Timer.Task(){
           public void run(){
               float newVol = powerUpMusic.getVolume() - volumeChange;
               if(newVol < 0){
                   powerUpMusic.stop();
                   fadeOutPowerTask.cancel();
               }
               else{
                   powerUpMusic.setVolume(newVol);
               }
           }
       }, 1, 1);
   }

   public static void changeMainMusicVolume(float volume){
       mainMusic.setVolume(volume);
   }


}
