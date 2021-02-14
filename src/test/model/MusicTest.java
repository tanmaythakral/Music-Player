package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;


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


//    @Test
//    void checkException() {
//        ExpectedException thrown = ExpectedException.none();
//        Music music = new Music("songs/hello.txt");
//        music.start();
//        thrown.expectMessage("File Not Found or Cant play, try again");
//    }
}