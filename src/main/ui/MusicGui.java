package ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import model.GetMusic;
import model.JsonReader;
import model.JsonWriter;
import model.Music;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class MusicGui {


    protected JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JList playlist;
    private JList list2;
    private JButton pauseButton;
    private JButton fastforwardButton;
    private JLabel image;
    private JButton playlistButton;
    private JButton nextButton;
    private JButton loadmusic;
    private JButton addtoplaylist;
    protected static Music playingMusic;
    protected static boolean playing;
    protected static boolean nextsong;
    boolean isPaused;
    protected static String songFolderPath;
    DefaultListModel<String> l1;
    protected static List<String> playlists;
    String currentlyPlaying;

    public void setNextsong(boolean nextsong) {
        MusicGui.nextsong = nextsong;
    }

    public boolean isNextsong() {
        return nextsong;
    }


    public List getPlaylists() {
        return playlists;
    }

    public String getSongFolderPath() {
        return songFolderPath;
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

    void changeCoverArt(String songPath) {
        File songfile = new File(songPath);
        try {
            Mp3File song = new Mp3File(songfile);
            if (song.hasId3v2Tag()) {
                ID3v2 id3v2tag = song.getId3v2Tag();
                byte[] imageData = id3v2tag.getAlbumImage();
                String songName = id3v2tag.getArtist();
                System.out.println(songName);
                //converting the bytes to an image
                try {
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
                    Icon iconImage = new ImageIcon(img);
                    image.setIcon(iconImage);
                } catch (Exception e) {
                    image.setIcon(new ImageIcon("C:\\Users\\tanma\\Downloads\\makise.jpg"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MusicGui() {

        initFlatLaf();

        pausebuttonListener();
        fastforwardbuttonListener();
        allsonglistListener();
        playlistbuttonListener();
        nextbuttonListenener();
        loadButtonListener();
        addtoplaylistListener();
    }

    private void allsonglistListener() {
        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                allsonglistAction();
            }
        });
    }

    private void fastforwardbuttonListener() {
        fastforwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fastforwardAction();
            }
        });
    }

    private void pausebuttonListener() {
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseAction();

            }
        });
    }

    private void playlistbuttonListener() {
        playlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActionWorker().execute();
            }
        });
    }

    private void nextbuttonListenener() {
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextsong = !nextsong;
            }
        });
    }

    private void addtoplaylistListener() {
        addtoplaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addtoplaylistAction();
            }
        });
    }

    private void loadButtonListener() {
        loadmusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMusicAction();
            }
        });
    }

    private void initFlatLaf() {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    private void addtoplaylistAction() {
        if (!l1.contains(currentlyPlaying)) {
            l1.addElement(currentlyPlaying);
            playlists.add(currentlyPlaying);

            try {
                JsonWriter jsonWriter = new JsonWriter(playlists, "data/playlistdata.json");
                jsonWriter.write();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        } else {
            l1.removeElement(currentlyPlaying);
            playlists.remove(currentlyPlaying);

            try {
                JsonWriter jsonWriter = new JsonWriter(playlists, "data/playlistdata.json");
                jsonWriter.write();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

    private void loadMusicAction() {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.setAcceptAllFileFilterUsed(false);
        j.setDialogTitle("Select a folder with songs");
        j.showOpenDialog(null);

        GetMusic getMusic = new GetMusic();
        String[] musiclist = getMusic.openFolder(j.getSelectedFile().getAbsolutePath()).toArray(new String[0]);
        songFolderPath = j.getSelectedFile().getAbsolutePath();
        System.out.println(songFolderPath);
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : musiclist) {
            model.addElement(s);
        }
        list2.setModel(model);
    }

    private void allsonglistAction() {
        if (playing) {
            playingMusic.stop();
            Music music = new Music(songFolderPath + "/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
            playingMusic = music;
            currentlyPlaying = list2.getModel().getElementAt(list2.getSelectedIndex()).toString();
            System.out.println(currentlyPlaying);
            playingMusic.start();
            isPaused = false;
            playing = true;
            changeCoverArt(songFolderPath + "/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
        } else {
            Music music = new Music(songFolderPath + "/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
            playingMusic = music;
            currentlyPlaying = list2.getModel().getElementAt(list2.getSelectedIndex()).toString();
            playingMusic.start();
            isPaused = false;
            playing = true;
            changeCoverArt(songFolderPath + "/" + list2.getModel().getElementAt(list2.getSelectedIndex()));
        }
    }

    private void fastforwardAction() {
        try {
            playingMusic.skip(10000);
        } catch (Exception exception) {
            System.out.println("End of Song");
        }
    }

    private void pauseAction() {
        if (isPaused) {
            playingMusic.resume();
            isPaused = false;
        } else {
            playingMusic.suspend();
            isPaused = true;
        }
    }


    private void createUIComponents() throws InvalidDataException, IOException, UnsupportedTagException {

        DefaultListModel<String> l1 = new DefaultListModel<>();
        list2 = new JList();

        JsonReader jsonReader = new JsonReader("data/playlistdata.json");

        try {
            this.playlists = jsonReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String song : this.playlists) {
            l1.addElement(song);
        }

        this.l1 = l1;
        System.out.println(this.playlists);
        playlist = new JList(this.l1);

        loadmusic = new JButton(new ImageIcon("src/icons/icons8-load-resume-template-48.png"));
        fastforwardButton = new JButton(new ImageIcon("src/icons/icons8-fast-forward-64.png"));
        addtoplaylist = new JButton(new ImageIcon("src/icons/icons8-add-song-50.png"));
        nextButton = new JButton(new ImageIcon("src/icons/icons8-next-page-50.png"));
        pauseButton = new JButton(new ImageIcon("src/icons/icons8-play-50.png"));
        image = new JLabel(new ImageIcon("C:\\Users\\tanma\\Downloads\\makise.jpg"));

    }
}
