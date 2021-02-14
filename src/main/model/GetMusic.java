package model;

import java.io.File;
import java.util.Arrays;
import java.util.List;

// class for getting songs from the user provided path
public class GetMusic {

    /*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: return the songs from the user provided path by reading it and then
     * storing it into an array.
     */
    public List<String> openFolder(String pathname) {
        File directoryPath = new File(pathname);
        List<String> contents = Arrays.asList(directoryPath.list());
        return contents;
    }

}
