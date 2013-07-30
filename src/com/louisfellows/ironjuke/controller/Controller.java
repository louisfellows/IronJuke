package com.louisfellows.ironjuke.controller;

import java.lang.reflect.InvocationTargetException;

import com.louisfellows.ironjuke.model.Album;
import com.louisfellows.ironjuke.model.DB;
import com.louisfellows.ironjuke.model.Track;
import com.louisfellows.ironjuke.view.Player;
import com.louisfellows.ironjuke.view.UI;

/**
 * Controller Class
 * 
 * Controls the system.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */

public class Controller {
    private static final int NUMBER_OF_ALBUMS = 4;
    private int albumStart;
    private final DB db;
    private final Player player;
    private final UI ui;

    public Controller() throws InterruptedException, InvocationTargetException {
        db = new DB();
        player = new Player();
        player.setController(this);

        ui = new UI(this);
        albumStart = 0;

        UpdateAlbums(albumStart);
    }

    /**
     * Adds a random track to the Playlist
     */
    public void addRandom() {
        Album album = db.getRandomAlbum();
        Track track = album.getRandomTrack();
        player.addTrack(track);
    }

    /**
     * Stops the currently playing track
     */
    public void cancelCurrent() {
        player.stopCurrent();
    }

    /**
     * Clears the current track information from the playbar.
     */
    public void clearPlayingTrack() {
        ui.clearPlayingTrack();
    }

    /**
     * @return The Music Player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Moves the current page of albums left.
     */
    public void pageLeft() {
        albumStart -= NUMBER_OF_ALBUMS;
        if (albumStart < 0) {
            albumStart = db.getNumberAlbums() - NUMBER_OF_ALBUMS;
        }
        UpdateAlbums(albumStart);
    }

    /**
     * Moves the current page of albums right.
     */
    public void pageRight() {
        albumStart += NUMBER_OF_ALBUMS;
        if (albumStart > db.getNumberAlbums() - NUMBER_OF_ALBUMS) {
            albumStart = 0;
        }
        UpdateAlbums(albumStart);
    }

    /**
     * Update the string in the Playbar selection panel
     * 
     * @param selStr
     *            the new string
     */
    public void selUpdated(String selStr) {
        ui.setPlaybarSelectionString(selStr);
    }

    /**
     * Handles the addition of a new track to the playlist
     * 
     * @param album
     * @param track
     */
    public void TrackNumberEntered(Integer album, Integer track) {
        try {

            Album a = db.getAlbum(album);
            Track t = a.getTrack(track);
            player.addTrack(t);

        } catch (Exception e) {
            ui.setPlaybarSelectionString("No Track");
        }
    }

    /**
     * Updates the albums on the main screen.
     * 
     * @param startAlbum
     *            The album number of the first album to display.
     */
    public void UpdateAlbums(int startAlbum) {

        for (int i = 0; i < NUMBER_OF_ALBUMS; i++) {
            try {
                Album a = db.getAlbum(startAlbum + i);
                ui.setUpAlbum(i, a.getCover(), a.getArtist(), a.getTitle(), a.getTrackList(), (startAlbum + (i + 1)) + "");
            } catch (Exception e) {
                ui.hideAlbum(i);
            }
        }

        ui.repaint();
    }

    /**
     * Updates the playbar with information on the currently playing track.
     * 
     * @param track
     *            The new track currently playing
     */
    public void updatePlayingTrack(Track track) {
        ui.updatePlaybarTrack(track.getTitle(), track.getAlbum().getTitle(), track.getAlbum().getArtist(), track.getAlbum().getCover());
    }
}
