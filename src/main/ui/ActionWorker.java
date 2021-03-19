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
                Music music = new Music("data/songs/" + songs);
                musicGui.changeCoverArt("data/songs/" + songs);
                musicGui.setPlayingMusic(music);
                musicGui.getPlayingMusic().start();
                while (music.isAlive()) {
                    if (musicGui.isNextsong()) {
                        break;
                    }
                }
                if (musicGui.isNextsong()) {
                    musicGui.setNextsong(false);
                    musicGui.getPlayingMusic().stop();
                    continue;
                }
            }
        } else {
            for (Object songs : musicGui.getPlaylists()) {
                Music music = new Music("data/songs/" + songs);
                musicGui.changeCoverArt("data/songs/" + songs);
                musicGui.setPlayingMusic(music);
                musicGui.getPlayingMusic().start();
                while (music.isAlive()) {
                    if (musicGui.isNextsong()) {
                        break;
                    }
                }
                if (musicGui.isNextsong()) {
                    musicGui.setNextsong(false);
                    musicGui.getPlayingMusic().stop();
                    continue;
                }
            }
        }

        return null;
    }
}
