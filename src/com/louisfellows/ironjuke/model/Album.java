package com.louisfellows.ironjuke.model;

import java.util.HashMap;
import java.util.Random;

import com.louisfellows.ironjuke.exceptions.TrackNotFoundException;

/**
 * Represents an album.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class Album {
    public String artist;
    public String coverLocation;
    public String title;
    public HashMap<Integer, Track> tracks;

    public Album(String title, String artist, String coverLocation) {
        this.title = title;
        this.artist = artist;
        this.coverLocation = coverLocation;

        tracks = new HashMap<Integer, Track>();
    }

    /**
     * Get the albums artist
     * 
     * @return the album artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Get the path to the albums cover art
     * 
     * @return the path to the albums cover art
     */
    public String getCover() {
        return coverLocation;
    }

    /**
     * Returns a random track from the album
     * 
     * @return a random track
     */
    public Track getRandomTrack() {
        Random random = new Random();
        Track track = null;
        while (track == null) {
            int alNo = random.nextInt(tracks.size());
            track = tracks.get(alNo);
        }
        return track;
    }

    /**
     * Get the title of the album
     * 
     * @return the title of the album
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a list of tracks on the album hashed against their track number.
     * 
     * @return
     */
    public HashMap<Integer, Track> getTrack() {
        return tracks;
    }

    /**
     * Gets a track from the album
     * 
     * @param trackNumber
     *            the track number of the track to retrieve
     * @return the track
     * @throws TrackNotFoundException
     *             if the track cannot be found
     */
    public Track getTrack(Integer trackNumber) throws TrackNotFoundException {
        if (tracks.containsKey(trackNumber)) {
            return tracks.get(trackNumber);
        } else {
            throw new TrackNotFoundException();
        }
    }

    /**
     * Gets an HTML representation of the tracklist.
     * 
     * @return an HTML representation of the tracklist.
     */
    public String getTrackList() {
        String trackStr = "";
        Integer counter = 1;
        for (Track track : tracks.values()) {
            trackStr += counter + ". " + track.getTitle() + "<br>";
            counter++;
        }
        return trackStr;
    }

    @Override
    public String toString() {
        String trackStr = "";
        for (Track track : tracks.values()) {
            trackStr += track.getTitle() + ", ";
        }
        return artist + " - " + title + ": (" + trackStr + ")";
    }
}
