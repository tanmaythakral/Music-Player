package model;

import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;

// Class for initialling the music player and player related methods
public class Music extends Thread {
    String songPath;

    //REQUIRES: songPath to be string with non-zero length that corresponds to an existing path
    // EFFECTS : constructor method for music class
    public Music(String songPath) {
        this.songPath = songPath;
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialises the player for playing songs
     */
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