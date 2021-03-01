package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class JsonReader {
    List songlist = new ArrayList<String>();
    String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public List read() throws IOException {

        String jsonData = readFile(source);
        JSONArray jsonObject = new JSONArray(jsonData);

        JSONObject name = (JSONObject) jsonObject.get(0);

        for (int i = 0; i < name.names().length(); i++) {
            songlist.add(name.get(name.names().getString(i)));
        }

        return songlist;
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
