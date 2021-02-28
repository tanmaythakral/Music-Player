package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            new MusicPlayerApp();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}