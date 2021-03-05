package model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.sound.sampled.LineUnavailableException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

// Class for initialling the music player and player related methods
public class Music extends Thread {
    String songPath;
    AdvancedPlayer player;
    int pausedOnFrame = 0;

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
//            long duration = 0;
//            try {
//                duration = Objects.requireNonNull(fileInputStream).getChannel().size() / 128;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(duration);

            AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
            this.player = player;
            player.play();
//            player.setPlayBackListener(new PlaybackListener() {
//                public void playbackFinished(PlaybackEvent event) {
//                    System.err.println(event.getFrame());
//                    pausedOnFrame = event.getFrame();
//                }
//            });
        } catch (FileNotFoundException | JavaLayerException e) {
            System.out.print("File Not Found or Cant play, try again");
        }
    }

//    public void playsong() throws Exception {
//        Thread th = new Thread() {
//            public void run() {
//                try {
//                    player.play(pausedOnFrame, Integer.MAX_VALUE);
//                } catch (JavaLayerException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        th.start();
//    }
//
//    public void fastforward() {
//        pausemusic();
//        long nextFrame = (long) (pausedOnFrame+0.02*audioLength);
//        if (nextFrame < audioLength)
//            play();
//    }
//
//    public void pausemusic() throws LineUnavailableException {
//        player.stop();
//    }

}