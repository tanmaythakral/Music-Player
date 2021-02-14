package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetMusicTest {
    @BeforeEach
    void init() {
        GetMusic getMusic = new GetMusic();
    }
    @Test
    void openFolderTest() {
        GetMusic getMusic = new GetMusic();
        List<String> contents = getMusic.openFolder("songs");
        List<String> result =new ArrayList<String>();

        result.add("a_test_sound.mp3");
        result.add("Dance-Monkey.mp3");
        result.add("Owl-City-Fireflies.mp3");

        assertEquals(contents,result);
    }
}
