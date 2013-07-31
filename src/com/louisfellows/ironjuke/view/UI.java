package com.louisfellows.ironjuke.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.controller.KeyListen;
import com.louisfellows.ironjuke.controller.WindowCloseListener;

/**
 * Creates and handles the UI
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class UI {

    private JFrame frame;
    private final ArrayList<AlbumView> panels;
    private Controller controller;
    private PlayBar playBar;

    public UI(final Controller newcontroller) throws InterruptedException, InvocationTargetException {
        panels = new ArrayList<AlbumView>();
        setController(newcontroller);

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                playBar = new PlayBar();
                frame = new JFrame();

                frame.setLayout(null);

                frame.setUndecorated(true);
                frame.setBounds(0, 0, 800, 600);

                frame.addWindowListener(new WindowCloseListener());

                JLabel bg = new JLabel();
                ImageIcon ii = new ImageIcon("./img/bg.png");
                bg.setIcon(ii);
                bg.setBounds(0, 0, 800, 600);

                AlbumView jp;

                jp = new AlbumView();
                jp.setBounds(10, 0, 390, 250);
                frame.add(jp);
                panels.add(jp);

                jp = new AlbumView();
                jp.setBounds(10, 250, 390, 250);
                frame.add(jp);
                panels.add(jp);

                jp = new AlbumView();
                jp.setBounds(400, 0, 390, 250);
                frame.add(jp);
                panels.add(jp);

                jp = new AlbumView();
                jp.setBounds(400, 250, 390, 250);
                frame.add(jp);
                panels.add(jp);

                playBar.setBounds(0, 500, 800, 100);
                frame.add(playBar);
                frame.setVisible(true);

                frame.addKeyListener(new KeyListen(controller));

                frame.add(bg);
            }
        });
    }

    /**
     * Sets the controller object for this object
     * 
     * @param controller
     *            the new controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Sets up a visible AlbumView panel
     * 
     * @param albumPageNo
     *            the number of the panel to update
     * @param cover
     *            the path to the cover art
     * @param artist
     *            the artist of the album
     * @param title
     *            the title of the album
     * @param track
     *            a tracklist for the album
     * @param displayNo
     *            the display number for the album
     */
    public void updateAlbum(int albumPageNo, String cover, String artist, String title, String track, String displayNo) {
        AlbumView a = panels.get(albumPageNo);
        a.setVisible(true);
        a.setArtist(artist);
        a.setTitle(title);
        a.setCover(cover);
        a.setTrackList(track);
        a.setAlbumNo(displayNo);
    }

    /**
     * Hides one of the visible AlbumView panels
     * 
     * @param albumPageNo
     *            the number of the AlbumView to hide
     */
    public void hideAlbum(int albumPageNo) {
        AlbumView a = panels.get(albumPageNo);
        a.setVisible(false);
    }

    /**
     * Clears the currently playing track from the playbar
     */
    public void clearPlayingTrack() {
        playBar.clearPlayingTrack();
    }

    /**
     * Sets the selection string in the playbar
     * 
     * @param selection
     *            the new selection string
     */
    public void setPlaybarSelectionString(String selection) {
        playBar.setSelection(selection);
    }

    /**
     * Updates the currently playing track in the playbar
     * 
     * @param trackTitle
     *            the title of the new track
     * @param albumTitle
     *            the album title of the new track
     * @param artist
     *            the artist of the new track
     * @param coverPath
     *            the path to the cover art for the new track
     */
    public void updatePlaybarTrack(String trackTitle, String albumTitle, String artist, String coverPath) {
        playBar.setTrack(trackTitle);
        playBar.setAlbum(albumTitle);
        playBar.setArtist(artist);

        playBar.setCover(coverPath);
    }
}
