package ui;

import model.GetMusic;
import model.JsonReader;
import model.JsonWriter;
import model.Music;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Music Player Application
public class MusicPlayerApp {
    boolean keepGoing;
    Music playingMusic;
    List<String> songsList = new ArrayList<String>();
    boolean playing;
    boolean stopPlaylist;
    String filePath;
    String songName;
    Scanner fileName = new Scanner(System.in);
    Scanner input = new Scanner(System.in);
    Scanner command = new Scanner(System.in);

    // EFFECTS: runs the Music Player application
    public MusicPlayerApp() throws FileNotFoundException {
        runMusicApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input and prints the songs present the the music directory
    private void runMusicApp() throws FileNotFoundException {

        System.out.println("Hi q(≧▽≦q) "
                + "\n To play your music please enter the absolute path to the music directory");
        String filename = fileName.nextLine();
        GetMusic getMusic = new GetMusic();
        this.filePath = filename;

        try {
            System.out.println("List of Songs in the specified directory are:");

            for (int i = 0; i < getMusic.openFolder(filename).size(); i++) {
                System.out.println(getMusic.openFolder(filename).get(i));
            }

        } catch (Exception exception) {
            System.out.println("Oops! Enter Valid Directory Path");
            keepGoing = false;
            runMusicApp();
        }

        getSongName();
        playMusic();
    }


    private void getSongName() {
        System.out.println("Enter song name to play (with extension)");
        String name = input.nextLine();
        this.songName = name;
    }

    // MODIFIES: this
    // EFFECTS: plays the music from user input
    private void playMusic() throws FileNotFoundException {
        try {
            Music music = new Music(filePath + "\\" + songName);
            this.playingMusic = music;
            music.start();
            System.out.println("Now playing :" + songName);

        } catch (Exception exception) {
            System.out.println("Song not playable");
        }
        commands();
    }

    // MODIFIES: this
    // EFFECTS: list of user executable commands
    private void commands() throws FileNotFoundException {
        while (true) {
            String commands = command.nextLine();
            if (commands.equals("-pause")) {
                pause();
            } else if (commands.equals("-res")) {
                resume();
            } else if (commands.equals("-stop")) {
                stop();
                break;
            } else if (commands.equals("-ns")) {
                next();
            } else if (commands.equals("-pl")) {
                playlist();
            } else if (commands.equals("-modpl")) {
                modifyplaylist();
            } else {
                commandList();
            }
        }
    }


//    private void fastforward()  {
////        try {
////            playingMusic.skip(10000);
////        } catch (Exception e) {
////            System.out.println("End of Song");
////        }
//        try {
//            playingMusic.setVolume(-80);
//        } catch (Exception e) {
//            System.out.println("dfvn");
//        }
//
//    }

    // EFFECTS: prints the user-made playlist from the stored json file
    private void playlist() {
        JsonReader jsonReader = new JsonReader("data/playlistdata.json");

        try {
            this.songsList = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String song : songsList) {
            System.out.println(song);
        }
    }

    //EFFECT : plays the usermade playlist
    private void playplaylist() {
        stopPlaylist = false;
        playlist();
        int counter = 0;
        for (String song : songsList) {
            System.out.println("\nNow playing :" + song);
            counter++;
            Music music = new Music(filePath + "\\" + song);
            this.playingMusic = music;
            playingMusic.start();
            playlistcommands();

            if (stopPlaylist) {
                break;
            }
        }
    }


    public void playlistcommands() {
        while (playingMusic.isAlive()) {
            String commands = command.nextLine();
            if (commands.equals("-pause")) {
                pause();
            } else if (commands.equals("-res")) {
                resume();
            } else if (commands.equals("-shuffle")) {
                Collections.shuffle(songsList);
            } else if (commands.equals("-stop")) {
                stopPlaylist = true;
                playingMusic.stop();
                break;
            } else if (commands.equals("-ns")) {
                pause();
                break;
            }
        }
        if (!playingMusic.isAlive()) {
            System.out.println("playing next song");
        }
    }

    //EFFECT : Displays the user options for modifying the playlist
    private void modifyplaylist() {
        System.out.println("To add songs to playlist, -a"
                + "\n To remove songs from playlist -r");
        String commands = command.nextLine();

        if (commands.equals("-a")) {
            try {
                addplaylist();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (commands.equals("-r")) {
            try {
                removefromplaylist();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            modifyplaylist();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds user input to the playlist
    private void addplaylist() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader("data/playlistdata.json");
        try {
            this.songsList = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Add songs in playlist, type name: ");
        getSongName();
        songsList.add(songName);

        List<String> songsList = this.songsList.stream()
                .distinct()
                .collect(Collectors.toList());

        JsonWriter jsonWriter = new JsonWriter(songsList, "data/playlistdata.json");
        jsonWriter.write();

    }

    // MODIFIES: this
    // EFFECTS: removes user input from the playlist
    private void removefromplaylist() throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader("data/playlistdata.json");
        try {
            this.songsList = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Add songs in playlist, type name: ");
        getSongName();

        List<String> songsList = this.songsList.stream()
                .distinct()
                .collect(Collectors.toList());

        songsList.remove(songName);

        JsonWriter jsonWriter = new JsonWriter(songsList, "data/playlistdata.json");
        jsonWriter.write();

    }

    public List<String> getSongsList() {
        return songsList;
    }

    // MODIFIES: this
    // EFFECTS: pauses the song
    private void pause() {
        playingMusic.suspend();
        playing = false;
    }

    // MODIFIES: this
    // EFFECTS: resumes the song
    private void resume() {
        playingMusic.resume();
        playing = true;
    }

    // MODIFIES: this
    // EFFECTS: stops the player
    private void stop() {
        playingMusic.stop();
        playing = false;
        System.out.println("GoodBye!");
    }

    // MODIFIES: this
    // EFFECTS: plays next song from user input
    private void next() throws FileNotFoundException {
        playingMusic.stop();
        getSongName();
        Music music = new Music(filePath + "\\" + songName);
        playMusic();
    }

    public boolean getPlaying() {
        return playing;
    }

    //EFFECT : prints the list of user executable commands
    private void commandList() {

        System.out.println("To pause the song , type -pause"
                + "\n To  resume the song , type -re"
                + "\n To stop the song, type -stop"
                + "\n To select the next song ,type -ns"
                + "\nand the enter the song name."
                + "\nTo check playlist type -pl"
                + "\nTo modify the playlist, type -modpl");

    }
}
