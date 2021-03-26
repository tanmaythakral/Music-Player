package model;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonWriter {
    JSONArray jsonObject;
    List songlist;
    String source;

    // MODIFIES : this
    // EFFECT: Constructs the Json Writer Class
    public JsonWriter(List songlist,String source) throws FileNotFoundException {
        JSONArray jo = new JSONArray();
        this.jsonObject = jo;
        this.songlist = songlist;
        this.source = source;
    }

    // MODIFIES : this
    // EFFECT: Writes the objects from a list to a  Json file
    public void write() throws FileNotFoundException {


        LinkedHashMap linkedHashMap = new LinkedHashMap(songlist.size());

        int counter = 1;
        for (Object songs : songlist) {
            linkedHashMap.put(counter,songs);
            counter += 1;
        }

        jsonObject.put(linkedHashMap);

        PrintWriter pw = new PrintWriter(source);
        pw.write(String.valueOf(jsonObject));

        pw.flush();
        pw.close();
    }

}
