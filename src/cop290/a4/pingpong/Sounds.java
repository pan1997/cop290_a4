package cop290.a4.pingpong;

import cop290.a4.Main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kritarth on 30/4/16.
 */
public class Sounds {

    String sounds[] = {"go","bat","obs","welcome"};
    AudioInputStream go;
    AudioInputStream bat;
    AudioInputStream obs;
    AudioInputStream welcome;

    AudioInputStream as[]= {go, bat, obs, welcome};

    public Sounds() {}

    public AudioInputStream[] initSound(){
        for (int i=0;i<sounds.length; i++){
            try {
                URL test0 = this.getClass().getClassLoader().getResource("./"+sounds[i]+".wav");

                System.out.println(test0);

                AudioInputStream test = AudioSystem.getAudioInputStream(test0);

                //InputStream test = new BufferedInputStream(audioSrc);
                //URL test = AudioMixer.class.getResource("./"+sounds[i]+".wav");
                //as[i] = AudioSystem.getAudioInputStream(test);
                as[i] = test;
                System.out.println("initS: "+ i);
            } catch (Exception ee) {
                System.out.println("Sound "+ sounds[i] +" error : "+ee);
            }
        }
        return as;
    }

    public void playSound(String s){
        switch (s) {
            case "go" :
                try {
                    DataLine.Info info = new DataLine.Info(Clip.class, as[0].getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(as[0]);
                    clip.loop(0);
                    while(clip.isRunning()){}
                    wait(1);
                    //clip.flush();
                    //clip.close();
                } catch (Exception ex){
                    System.out.println(" error :"+ex);
                }
                break;

            case "bat" :
                try {
                    DataLine.Info info = new DataLine.Info(Clip.class, as[1].getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(as[1]);
                    clip.setMicrosecondPosition(500000);
                    clip.loop(0);
                    while(clip.isRunning()){}
                    //wait(1);
                    //clip.flush();
                    //clip.close();
                } catch (Exception ex){
                    System.out.println(" error : "+ex);
                }
                break;
            case "obs" :
                try {
                    DataLine.Info info = new DataLine.Info(Clip.class, as[2].getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(as[2]);
                    clip.loop(0);
                    while(clip.isRunning()){}
                    //wait(1);
                    //clip.flush();
                    //clip.close();
                } catch (Exception ex){
                    System.out.println(" error : "+ex);
                }
                break;
            case "welcome" :
                try {
                    DataLine.Info info = new DataLine.Info(Clip.class, as[3].getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(as[3]);
                    clip.loop(0);
                    while(clip.isRunning()){}
                    System.out.println("Welcome sound played");
                    //clip.flush();
                    //clip.close();
                } catch (Exception ex){
                    System.out.println(" error : "+ex);
                }
                break;
        }
    }
}
