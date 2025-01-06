package main;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL sonudURL[] = new URL[30];

    public Sound(){
        sonudURL[0] = getClass().getResource("/sound/OutThere.wav");
        sonudURL[1] = getClass().getResource("/sound/powerup.wav");
        sonudURL[2] = getClass().getResource("/sound/punch.wav");
        sonudURL[3] = getClass().getResource("/sound/receivedamage.wav");
        sonudURL[4] = getClass().getResource("/sound/coin.wav");
        sonudURL[5] = getClass().getResource("/sound/doorOpening.wav");
        sonudURL[6] = getClass().getResource("/sound/blocked.wav");
        sonudURL[7] = getClass().getResource("/sound/punch.wav");
        sonudURL[8] = getClass().getResource("/sound/receivedamage.wav");
        sonudURL[9] = getClass().getResource("/sound/punch.wav");
        sonudURL[10] = getClass().getResource("/sound/levelup.wav");
        sonudURL[11] = getClass().getResource("/sound/gameover.wav");


    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonudURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch (Exception e){

        }

    }

    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();

    }



}
