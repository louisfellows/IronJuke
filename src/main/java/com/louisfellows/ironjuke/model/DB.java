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

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import com.louisfellows.ironjuke.exceptions.AlbumNotFoundException;

/**
 * Creates and manages a Database of tracks.
 * 
 * Contains functions to discover all tracks in a given directory. Assumes a
 * folder structure of /music_root/Artist/Album/[tracks]
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class DB {

    private static final int TRACKS_PER_ALBUM = 13;
    private static final String DEFAULT_MUSIC_ROOT = "G:/My Music";
    private final HashMap<Integer, Album> albums;
    private File root = new File(DEFAULT_MUSIC_ROOT);

    /**
     * Constructor. Attempts to read the music root location from a config file.
     * If this cannot be found uses the default set in DEFAULT_MUSIC_ROOT
     * constant.
     */
    public DB() {
        albums = new HashMap<Integer, Album>();

        try {
            String s = readFileAsString("./locationStr.txt");
            root = new File(s);
        } catch (IOException e) {
        }

        scanDirectory(root);
    }

    /**
     * returns the album with the given display number.
     * 
     * @param albumNumber
     *            the display number of the requested album
     * @return the album for the given number
     * @throws AlbumNotFoundException
     *             if the album cannot be found
     */
    public Album getAlbum(Integer albumNumber) throws AlbumNotFoundException {
        if (albums.containsKey(albumNumber)) {
            return albums.get(albumNumber);
        } else {
            throw new AlbumNotFoundException();
        }
    }

    /**
     * scans a directory for compatible music files (currently limited to mp3s)
     * to be added to the database. This method is recursive and will check each
     * directory under the first directory it is given. It will continue until
     * all folders have been searched or 999 albums have been identified.
     * 
     * Will attempt to identify a cover image for each album. Will use images
     * with the following priority
     * <ol>
     * <li>image file named 'cover.jpg'</li>
     * <li>image file named 'cover.png'</li>
     * <li>the first image file in the directory</li>
     * <li>the default 'no image' image</li>
     * </ol>
     * 
     * Assumes a folder structure of /music_root/Artist/Album/[tracks]
     * 
     * @param directory
     */
    private void scanDirectory(File directory) {

        if (!directory.isDirectory()) {
            // finish recursing if the given file is not a directory
            return;
        }

        if (albums.size() >= 999) {
            // finish recursing if we are already full of albums
            return;
        }

        FilenameFilter filefilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mp3");
            }
        };

        File[] mp3s = directory.listFiles(filefilter);

        if (mp3s.length > 0) {
            // Check for an Artist/Album/tracks directory structure.
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
                } else if ((new File(directory.getAbsolutePath() + "cover.png")).exists()) {
                    cover = directory.getAbsolutePath() + "cover.png";
                } else if (imgs.length > 0) {
                    cover = imgs[0].getAbsolutePath();
                } else {
                    cover = "img/noimg.png";
                }

                // Split album into groups of TRACKS_PER_ALBUM tracks. this
                // stops there being too many tracks to display on one card.
                for (int i = 0; i < mp3s.length; i += TRACKS_PER_ALBUM) {

                    Album album;
                    if (mp3s.length > TRACKS_PER_ALBUM) {
                        String part = Integer.toString((i / TRACKS_PER_ALBUM) + 1);
                        album = new Album(title + " (Part " + part + ")", artist, cover);
                    } else {
                        album = new Album(title, artist, cover);
                    }

                    for (int j = i; (j < i + TRACKS_PER_ALBUM) && (j < mp3s.length); j++) {
                        File mp3 = mp3s[j];
                        String ttitle;

                        AudioFile f;
                        try {
                            f = AudioFileIO.read(mp3);
                            Tag tag = f.getTag();
                            ttitle = tag.getFirst(FieldKey.TITLE);
                            if (ttitle.length() == 0) {
                                ttitle = mp3.getName();
                                ttitle = ttitle.substring(0, ttitle.length() - 4); // remove
                                                                                   // '.mp3'
                            }
                        } catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
                            // Error occured, use filename
                            ttitle = mp3.getName();
                            ttitle = ttitle.substring(0, ttitle.length() - 4); // remove
                                                                               // '.mp3'
                        }

                        Track track = new Track(ttitle, mp3.getAbsolutePath(), album);
                        album.getTracks().put(album.getTracks().size(), track);
                    }

                    albums.put(albums.size(), album);
                }
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
            scanDirectory(dir);
        }
    }

    /**
     * Returns the total number of albums in the system.
     * 
     * @return the total number of albums
     */
    public int getNumberAlbums() {
        return albums.size();
    }

    /**
     * returns a random albums
     * 
     * @return a randomly picked albums
     */
    public Album getRandomAlbum() {
        Random random = new Random();
        Album album = null;
        while (album == null) {
            int alNo = random.nextInt(albums.size());
            album = albums.get(alNo);
        }
        return album;
    }

    /**
     * Returns the contents of the file at the given filepath as a String.
     * 
     * @param filePath
     *            the file to read
     * @return the contents of the file as a string
     * @throws java.io.IOException
     *             if the file cannot be read.
     */
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
