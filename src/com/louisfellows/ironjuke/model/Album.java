package com.louisfellows.ironjuke.model;

import java.util.HashMap;
import java.util.Random;

public class Album {
    public String title;
    public String artist;
    public HashMap<Integer, Track> tracks;
    public String coverLocation;

    public Album(String title, String artist, String coverLocation) {
        this.title = title;
        this.artist = artist;
        this.coverLocation = coverLocation;

        tracks = new HashMap<Integer, Track>();
    }

    public HashMap<Integer, Track> getTrack() {
        return tracks;
    }

    public void setM_Track(HashMap<Integer, Track> track) {
        this.tracks = track;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getCover() {
        return coverLocation;
    }

    public Track getTrack(Integer trackNumber) throws Exception {
        if (tracks.containsKey(trackNumber)) {
            return tracks.get(trackNumber);
        } else {
            throw new Exception();
        }
    }

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

    public Track getRandomTrack() {
        Random random = new Random();
        Track track = null;
        while (track == null) {
            int alNo = random.nextInt(tracks.size());
            track = tracks.get(alNo);
        }
        return track;
    }
}
