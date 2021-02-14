package model;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music extends Thread {
    String songPath;

    public Music(String songPath) {
        this.songPath = songPath;
    }

    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(songPath);
            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
            player.play();

        } catch (Exception e) {
            System.out.println("File Not Found or Cant play, try again");
        }

    }
}