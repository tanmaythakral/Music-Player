package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetMusicTest {
    @BeforeEach
    void init() {
        GetMusic getMusic = new GetMusic();
    }
    @Test
    void openFolderTest() {
        GetMusic getMusic = new GetMusic();
        getMusic.openFolder("songs");

        assertEquals(getMusic.openFolder("songs"), "songs");
    }
}
