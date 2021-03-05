package ui;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws JavaLayerException, FileNotFoundException {

        try {
            new MusicPlayerApp();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}