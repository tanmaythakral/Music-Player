package ui;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws JavaLayerException, IOException {
//        try {
//            new MusicPlayerApp();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        MusicGui musicInterface = new MusicGui();
        JFrame frame = new JFrame("App");
        frame.setPreferredSize(new Dimension(1800, 1200));
        frame.setContentPane(musicInterface.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}