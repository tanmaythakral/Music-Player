package model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

// Class for initialling the music player and player related methods
public class Music extends Thread {
    String songPath;
    Player player;

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

            Player player = new Player(fileInputStream);
            this.player = player;
            this.player.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            System.out.print("File Not Found or Cant play, try again");
        }
    }

    public void skip(int skipnum) throws JavaLayerException {
        player.skipMilliSeconds(skipnum);
    }

}