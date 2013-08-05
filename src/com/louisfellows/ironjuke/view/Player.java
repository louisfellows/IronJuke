package com.louisfellows.ironjuke.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.model.Track;

/**
 * Handles song playback and the play queue.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class Player extends PlaybackListener {

    private final ArrayDeque<Track> tracks;
    private AdvancedPlayer player;
    private Boolean playing;
    private Controller controller;

    public Player() {

        tracks = new ArrayDeque<Track>();
        playing = false;
    }

    /**
     * Set the controller object for this player.
     * 
     * @param controller
     *            the new controller object
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Starts playing the next song in the queue.
     */
    public void playNext() {

        if (tracks.size() > 0) {
            final Track track = tracks.pollFirst();

            new Thread() {
                @Override
                public void run() {
                    try {
                        controller.updatePlayingTrack(track);
                        FileInputStream fIS = new FileInputStream(track.m_Location);
                        player = new AdvancedPlayer(fIS);
                        player.setPlayBackListener(controller.getPlayer());
                        playing = true;
                        player.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * Add a track to the queue.
     * 
     * @param track
     *            the track to add to the queue.
     */
    public void addTrack(Track track) {
        tracks.addLast(track);

        if (playing == false) {
            playing = true;
            playNext();
        }
    }

    /**
     * Stop playing the current track
     */
    public void stopCurrent() {
        player.stop();
    }

    /**
     * Actions to perform when a song finishes.
     */
    @Override
    public void playbackFinished(PlaybackEvent evt) {
        playing = false;
        controller.clearPlayingTrack();
        playNext();
    }

}
