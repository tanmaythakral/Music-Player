package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// class for getting songs from the user provided path
public class GetMusic {

    /*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: return the songs from the user provided path by reading it and then
     * storing it into an array.
     */
    public List<String> openFolder(String pathname) {
        File directoryPath = new File(pathname);
        List<String> allcontents = Arrays.asList(Objects.requireNonNull(directoryPath.list()));
        List<String> contents = new ArrayList<>();
        Pattern pattern = Pattern.compile(".mp3$", Pattern.CASE_INSENSITIVE);
        for (String names : allcontents) {
            Matcher matcher = pattern.matcher(names);
            if (matcher.find()) {
                contents.add(names);
            }
        }
        return contents;
    }
}
