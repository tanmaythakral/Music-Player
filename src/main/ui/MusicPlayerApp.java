package ui;

import model.GetMusic;
import model.JsonReader;
import model.JsonWriter;
import model.Music;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Music Player Application
public class MusicPlayerApp {
    boolean keepGoing;
    Music playingMusic;
    List<String> songsList = new ArrayList<String>();
    boolean playing;
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
            } else if (commands.equals("-resume")) {
                resume();
            } else if (commands.equals("-stop")) {
                stop();
                break;
            } else if (commands.equals("-next song")) {
                next();
            } else if (commands.equals("-playlist")) {
                playlist();
            } else if (commands.equals("-addtoplaylist")) {
                addplaylist();
            } else if (commands.equals("-play playlist")) {
                playplaylist();
            } else {
                commandList();
            }
        }
    }

//    private void playlistcommands() {
//        String commands = command.nextLine();
//        if (commands.equals("-pause")) {
//            pause();
//        } else if (commands.equals("-resume")) {
//            resume();
//        }  else if (commands.equals("-shuffle")) {
//            Collections.shuffle(songsList);
//        }  else {
//            commandList();
//        }
//    }


    // EFFECTS: prints the user-made playlist
    private void playlist() {
        JsonReader jsonReader = new JsonReader();
//        try {
//            this.songsList = jsonReader.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            this.songsList = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String song : songsList) {
            System.out.println(song);
        }
    }

    private void playplaylist() {

        for (String song : songsList) {
            Music music = new Music(filePath + "\\" + song);
            this.playingMusic = music;
            music.start();
            while (music.isAlive()) {
//                playlistcommands();
//                String commands = command.nextLine();
//                if (commands.equals("-stop playlist")) {
//                    break;
//                } else if (commands.equals("-next song")) {
//                    continue;
//                }
            }
            if (!music.isAlive()) {
                System.out.println("playing next song");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds user input to the playlist
    private void addplaylist() throws FileNotFoundException {
        System.out.println("Add songs in playlist, type name: ");
        getSongName();
        songsList.add(songName);

        JsonWriter jsonWriter = new JsonWriter(songsList,"data/playlistdata.json");
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
                + "\n To  resume the song , type -resume"
                + "\n To stop the song, type -stop"
                + "\n To select the next song ,type -next song"
                + "\nand the enter the song name."
                + "\nTo check playlist type -playlist"
                + "\nTo add to playlist, type -addtoplaylist");

    }
}
