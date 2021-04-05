package model;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MusicTest {

//    @BeforeEach
//    void runBefore() {
//        Music music = new Music("./data/songs/a_test_sound.mp3");
//        music.run();
//    }

    @Test
    void playTest() {
        Music music = new Music("./data/songs/a_test_sound.mp3");
        music.run();
        music.start();
        assertTrue(music.isAlive());
        music.stop();
    }


    @Test
    void stopTest() {
        Music music = new Music("./data/songs/a_test_sound.mp3");
        music.start();
        assertTrue(music.isAlive());
        music.stop();
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        assertFalse(music.isAlive());
        music.stop();

    }


    @Test
    void checkException() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Music music = new Music("./data/songs/tet.txt");
        music.run();

        assertEquals("File Not Found or Cant play, try again", outContent.toString());
        System.setOut(originalOut);

        music.stop();
    }

    @Test
    void checkException1() {
        Music music = new Music("./data/songs/tests.txt");
        music.run();
    }

}