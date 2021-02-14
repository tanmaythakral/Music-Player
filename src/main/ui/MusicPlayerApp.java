package ui;

import model.GetMusic;
import model.Music;

import java.util.Scanner;


public class MusicPlayerApp {
    boolean keepGoing;
    Music playingMusic;
    boolean playing;
    String filePath;
    String songName;
    Scanner fileName = new Scanner(System.in);
    Scanner input = new Scanner(System.in);
    Scanner command = new Scanner(System.in);

    public MusicPlayerApp() {
        runMusicApp();
    }

    private void runMusicApp() {

        System.out.println("Hi q(≧▽≦q) "
                + "\n To play your music please enter the absolute path to the music directory");
        String filename = fileName.nextLine();
        GetMusic getMusic = new GetMusic();
        this.filePath = filename;

        try {
            getMusic.openFolder(filename);
        } catch (Exception exception) {
            System.out.println("Oops! Enter Valid Directory Path");
            keepGoing = false;
            runMusicApp();
        }

        startSong();
    }

    public void startSong() {
        getSongName();
        playMusic();
    }

    private void getSongName() {
        System.out.println("Enter song name to play (with extension)");
        String name = input.nextLine();
        this.songName = name;
    }


    private void playMusic() {
        try {
            Music music = new Music(filePath + "\\" + songName);
            this.playingMusic = music;
            music.start();

        } catch (Exception exception) {
            System.out.println("Song not playable");
        }
        commands();
    }

    private void commands() {
        while (true) {
            String commands = command.nextLine();
            if (commands.equals("-pause")) {
                pause();
            } else if (commands.equals("-resume")) {
                resume();
            } else if (commands.equals("-stop")) {
                stop();
            } else if (commands.equals("-next")) {
                next();
            } else {
                commandList();
                System.out.println(playingMusic.isAlive());
            }
        }
    }

    private void pause() {
        playingMusic.suspend();
        playing = false;
    }

    private void resume() {
        playingMusic.resume();
        playing = true;
    }

    private void stop() {
        playingMusic.stop();
        playing = false;
    }

    private void next() {
        playingMusic.stop();
        getSongName();
        Music music = new Music(filePath + "\\" + songName);
        playMusic();
    }

    public boolean isPlaying() {
        return playing;
    }

    private void commandList() {

        System.out.println("To pause the song , type -pause"
                + "\n To  resume the song , type -resume"
                + "\n To stop the song, type -stop" + "\n To select the next song ,type -next"
                + "\nand the enter the song name.");

    }
}
