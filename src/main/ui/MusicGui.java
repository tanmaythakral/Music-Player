package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import model.GetMusic;
import model.JsonReader;
import model.Music;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class MusicGui {


    protected JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JList list1;
    private JList list2;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton fastforwardButton;
    private JLabel image;
    private JButton playlistButton;
    private JButton next;
    private JLabel titleImage;
    protected static Music playingMusic;
    protected static boolean playing;
    protected static boolean nextsong;
    List playlists;

    public void setNextsong(boolean nextsong) {
        MusicGui.nextsong = nextsong;
    }

    public boolean isNextsong() {
        return nextsong;
    }


    public List getPlaylists() {
        return playlists;
    }

    public void setPlaying(boolean playing) {
        MusicGui.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlayingMusic(Music playingMusic) {
        this.playingMusic = playingMusic;
    }

    public Music getPlayingMusic() {
        return playingMusic;
    }

    private void changeCoverArt(String songPath) {
        File songfile = new File(songPath);
        try {
            Mp3File song = new Mp3File(songfile);
            if (song.hasId3v2Tag()) {
                ID3v2 id3v2tag = song.getId3v2Tag();
                byte[] imageData = id3v2tag.getAlbumImage();
                String songName = id3v2tag.getArtist();
                System.out.println(songName);
                //converting the bytes to an image
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                Icon iconImage = new ImageIcon(img);
                image.setIcon(iconImage);
                //"C:\\Users\\tanma\\Downloads\\makise.jpg"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MusicGui() {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playingMusic.suspend();
            }
        });
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playingMusic.resume();
            }
        });
        fastforwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playingMusic.skip(10000);
                } catch (Exception exception) {
                    System.out.println("End of Song");
                }
            }
        });

        list2.addComponentListener(new ComponentAdapter() {
        });
        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (playing) {
                    playingMusic.stop();
                    Music music = new Music("data/songs/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
                    playingMusic = music;
                    playingMusic.start();
                    playing = true;
                    changeCoverArt("data/songs/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
                } else {
                    Music music = new Music("data/songs/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
                    playingMusic = music;
                    playingMusic.start();
                    playing = true;
                    changeCoverArt("data/songs/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
                }
            }
        });
        playlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActionWorker().execute();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextsong = !nextsong;
            }
        });
    }


    private void createUIComponents() throws InvalidDataException, IOException, UnsupportedTagException {
        GetMusic getMusic = new GetMusic();
        String musiclist[] = getMusic.openFolder("data/songs").toArray(new String[0]);
        list2 = new JList(musiclist);
        JsonReader jsonReader = new JsonReader("data/playlistdata.json");


        try {
            List playlists = jsonReader.read();
            playlists = jsonReader.read();
            this.playlists = playlists;
            list1 = new JList(playlists.toArray());
            System.out.println(playlists);

        } catch (IOException e) {
            e.printStackTrace();
        }
        list1 = new JList(musiclist);
        image = new JLabel(new ImageIcon("C:\\Users\\tanma\\Downloads\\makise.jpg"));
        titleImage = new JLabel(new ImageIcon("src\\icons\\sound-waves.png"));

    }
}
