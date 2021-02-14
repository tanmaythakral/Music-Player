package model;

import java.io.File;

public class GetMusic {

    public String openFolder(String pathname) {
        File directoryPath = new File(pathname);

        String[] contents = directoryPath.list();

        System.out.println("List of Songs in the specified directory are:");

        for (int i = 0; i < contents.length; i++) {
            System.out.println(contents[i]);
        }
        return pathname;
    }

}
