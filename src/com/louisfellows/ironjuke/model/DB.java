package com.louisfellows.ironjuke.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DB {

    private final HashMap<Integer, Album> albums;
    private File root = new File("G:/My Music");

    public DB() {
        albums = new HashMap<Integer, Album>();

        try {
            String s = readFileAsString("./locationStr.txt");
            root = new File(s);
        } catch (IOException e) {
        }

        System.out.println(root.getAbsolutePath());
        recurseDirectory(root);
    }

    public Album getAlbum(Integer albumNumber) throws Exception {
        if (albums.containsKey(albumNumber)) {
            return albums.get(albumNumber);
        } else {
            throw new Exception();
        }
    }

    private void recurseDirectory(File directory) {

        if (!directory.isDirectory()) {
            return;
        }

        if (albums.size() >= 999) {
            return;
        }

        FilenameFilter filefilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        };

        File[] MP3s = directory.listFiles(filefilter);

        if (MP3s.length > 0) {
            String[] details = directory.getAbsolutePath().split("\\" + File.separator);
            if (details.length > 3) {
                String artist = details[details.length - 2];
                String title = details[details.length - 1];
                String cover;

                FilenameFilter imgfilter = new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.indexOf("Small") > 0) {
                            return false;
                        }

                        if (name.endsWith(".jpg")) {
                            return true;
                        } else if (name.endsWith(".png")) {
                            return true;
                        } else if (name.endsWith(".bmp")) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };

                File[] imgs = directory.listFiles(imgfilter);

                if ((new File(directory.getAbsolutePath() + "cover.jpg")).exists()) {
                    cover = directory.getAbsolutePath() + "cover.jpg";
                    System.out.println(cover);
                } else if ((new File(directory.getAbsolutePath() + "cover.png")).exists()) {
                    cover = directory.getAbsolutePath() + "cover.png";
                    System.out.println(cover);
                } else if (imgs.length > 0) {
                    cover = imgs[0].getAbsolutePath();
                    System.out.println(cover);
                } else {
                    cover = "img/noimg.png";
                }

                Album album = new Album(title, artist, cover);

                for (File mp3 : MP3s) {
                    String ttitle = mp3.getName();
                    Track track = new Track(ttitle, mp3.getAbsolutePath(), album);
                    album.getTrack().put(album.getTrack().size(), track);
                }

                albums.put(albums.size(), album);
            }
        }

        FileFilter dirfilter = new FileFilter() {

            @Override
            public boolean accept(File dir) {
                return dir.isDirectory();
            }
        };

        List<File> dirs = Arrays.asList(directory.listFiles(dirfilter));

        Collections.sort(dirs);

        for (File dir : dirs) {
            recurseDirectory(dir);
        }
    }

    public int getNumberAlbums() {
        return albums.size();
    }

    public Album getRandomAlbum() {
        Random random = new Random();
        Album album = null;
        while (album == null) {
            int alNo = random.nextInt(albums.size());
            album = albums.get(alNo);
        }
        return album;
    }

    private static String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
