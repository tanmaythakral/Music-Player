package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MusicTest {

    @BeforeEach
    void runBefore() {
        Music music = new Music("songs/Dance-Monkey.mp3");
    }

    @Test
    void playTest() {
        Music music = new Music("songs/Dance-Monkey.mp3");
        music.start();

        assertTrue(music.isAlive());
    }

    @Test
    void pauseTest() {
        Music music = new Music("songs/Dance-Monkey.mp3");
        music.start();
        assertTrue(music.isAlive());

        music.suspend();
        assertTrue(music.isAlive());

    }

    @Test
    void stopTest() {
        Music music = new Music("songs/Dance-Monkey.mp3");
        music.start();
        assertTrue(music.isAlive());

        music.stop();
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        assertFalse(music.isAlive());
    }


    @Test
    void checkException() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Music music = new Music("songs/hello.txt");
        music.run();

        assertEquals("File Not Found or Cant play, try again", outContent.toString());
        System.setOut(originalOut);
    }

}