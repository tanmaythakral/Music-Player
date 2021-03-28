package ui;

import model.Music;

import javax.swing.*;

//Action worker class for the UI
public class ActionWorker extends SwingWorker<Void, Void> {
    MusicGui musicGui = new MusicGui();

    //Modifies: this
    @Override
    protected void done() {
        super.done();
        musicGui.setPlaying(false);
    }

    //Effect: plays the song in the background
    @Override
    protected Void doInBackground() throws Exception {
        if (musicGui.isPlaying()) {
            musicGui.getPlayingMusic().stop();
            for (Object songs : musicGui.getPlaylists()) {
                Music music = initMusic(songs);
                moveToNextSong(music);
            }
        } else {
            for (Object songs : musicGui.getPlaylists()) {
                Music music = initMusic(songs);
                moveToNextSong(music);
            }
        }

        return null;
    }

    //Effect: Moves to the next song
    private void moveToNextSong(Music music) {
        while (music.isAlive()) {
            if (musicGui.isNextsong()) {
                break;
            }
        }
        if (musicGui.isNextsong()) {
            musicGui.setNextsong(false);
            musicGui.getPlayingMusic().stop();
            return;
        }
    }

    //Effect: initialises the music
    //Modifies: this
    private Music initMusic(Object songs) {
        Music music = new Music(musicGui.getSongFolderPath() + "/" + songs);
        musicGui.changeCoverArt(musicGui.getSongFolderPath() + "/" + songs);
        musicGui.setPlayingMusic(music);
        musicGui.getPlayingMusic().start();
        return music;
    }
}
