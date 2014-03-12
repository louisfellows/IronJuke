package com.louisfellows.ironjuke.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
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
    private int numberOfAlbums = 4;
    private int albumStart;
    private final DB db;
    private final Player player;
    private final UI ui;

    public Controller() throws InterruptedException, InvocationTargetException {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        db = new DB();
        player = new Player();
        player.setController(this);

        ui = new UI(this, width, height);

        albumStart = 0;

        UpdateAlbums(albumStart);
    }

    public Controller(DB db, UI ui, Player player) throws InterruptedException, InvocationTargetException {
        this.db = db;
        this.ui = ui;
        this.player = player;

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
        albumStart -= numberOfAlbums;
        if (albumStart < 0) {
            albumStart = db.getNumberAlbums() - numberOfAlbums;
        }
        UpdateAlbums(albumStart);
    }

    /**
     * Moves the current page of albums right.
     */
    public void pageRight() {
        albumStart += numberOfAlbums;
        if (albumStart > db.getNumberAlbums() - numberOfAlbums) {
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
            if (track == -1) {
                for (Track t : a.getTracks().values()) {
                    player.addTrack(t);
                }
            } else {
                Track t = a.getTrack(track);
                player.addTrack(t);
            }

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

        for (int i = 0; i < numberOfAlbums; i++) {
            try {
                Album a = db.getAlbum(startAlbum + i);
                ui.updateAlbum(i, a.getCover(), a.getArtist(), a.getTitle(), a.getTrackTitles(), (startAlbum + (i + 1)) + "");
            } catch (Exception e) {
                ui.hideAlbum(i);
            }
        }
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

    /**
     * sets the total number of albums visible on screen at once.
     * 
     * @param numberOfAlbums
     */
    public void setNumberOfAlbums(int numberOfAlbums) {
        this.numberOfAlbums = numberOfAlbums;
    }
}
