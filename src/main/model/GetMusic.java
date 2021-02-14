package model;

import java.io.File;

// class for getting songs from the user provided path
public class GetMusic {
    private String[] songs;

    /*
     * REQUIRES: accountName has a non-zero length
     * EFFECTS: return the songs from the user provided path by reading it and then
     * storing it into an array.
     */
    public String openFolder(String pathname) {
        File directoryPath = new File(pathname);

        String[] contents = directoryPath.list();

        for (int i = 0; i < contents.length; i++) {
            System.out.println(contents[i]);
        }
        return pathname;
    }

    public String[] getSongs() {
        return songs;
    }
}
