package ui;

import model.Music;

import javax.swing.*;

public class ActionWorker extends SwingWorker<Void, Void> {
    MusicGui musicGui = new MusicGui();

    @Override
    protected void done() {
        super.done();
        musicGui.setPlaying(false);
    }

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

    private Music initMusic(Object songs) {
        Music music = new Music(musicGui.getSongFolderPath() + "/" + songs);
        musicGui.changeCoverArt(musicGui.getSongFolderPath() + "/" + songs);
        musicGui.setPlayingMusic(music);
        musicGui.getPlayingMusic().start();
        return music;
    }
}
