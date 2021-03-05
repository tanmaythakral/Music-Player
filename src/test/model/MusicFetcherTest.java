package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MusicFetcherTest {
    @BeforeEach
    void init() {
        GetMusic getMusic = new GetMusic();
    }
    @Test
    void openFolderTest() {
        GetMusic getMusic = new GetMusic();

        List<String> result =new ArrayList<String>();
        result.add("Ariana Grande - One Last Time.mp3");
        result.add("Avicii-The-Nights.mp3");
        result.add("a_test_sound.mp3");
        result.add("Dance-Monkey.mp3");
        result.add("Kygo-Stargazing.mp3");
        result.add("Oh Wonder - Ultralife.mp3");
        result.add("OneRepublic - Counting Stars.mp3");
        result.add("Owl-City-Fireflies.mp3");
        result.add("twenty-one-pilots-Stressed-Out.mp3");

        assertEquals(result,getMusic.openFolder("songs"));
        assertTrue(getMusic.openFolder("songs").contains("a_test_sound.mp3"));
        assertTrue(getMusic.openFolder("./songs").contains("Dance-Monkey.mp3"));
        assertTrue(getMusic.openFolder("./songs").contains("Owl-City-Fireflies.mp3"));
        assertFalse(getMusic.openFolder("./songs").contains("anything"));
    }
}
