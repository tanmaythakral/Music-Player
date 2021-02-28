package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterReaderTest {
    List testlist = new ArrayList<String>();

    @BeforeEach
    void init() {
        testlist.add("Owl-City-Fireflies.mp3");
        testlist.add("Dance-Monkey.mp3");

        try {
            JsonWriter jsonWriter = new JsonWriter(testlist,"data/readtest.json");
            jsonWriter.write();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writetest() throws IOException {
        try {
            JsonWriter jsonWriter = new JsonWriter(testlist,"data/readtest.json");
            jsonWriter.write();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonReader jsonReader = new JsonReader();
        assertEquals(jsonReader.read(),testlist);
    }
}