package com.louisfellows.ironjuke.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.controller.KeyListen;
import com.louisfellows.ironjuke.controller.WindowCloseListener;
import com.louisfellows.ironjuke.view.util.ImageHandler;

/**
 * Creates and handles the UI
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class UI {

    private static final int MARGIN_LEFTRIGHT = 10;
    private static final int MARGIN_TOPBOTTOM = 10;
    private JFrame frame;
    private final ArrayList<AlbumView> panels;
    private Controller controller;
    private PlayBar playBar;

    public UI(final Controller newcontroller, final int width, final int height) throws InterruptedException, InvocationTargetException {
        panels = new ArrayList<AlbumView>();
        setController(newcontroller);

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                playBar = new PlayBar();
                frame = new JFrame();

                frame.setLayout(null);

                frame.setUndecorated(true);
                frame.setBounds(0, 0, width, height);

                frame.addWindowListener(new WindowCloseListener());

                JLabel bg = new JLabel();

                BufferedImage bi;
                try {
                    bi = ImageIO.read(new File("./img/bg.png"));
                    bi = ImageHandler.resizeImage(bi, bi.getType(), width, height);
                    ImageIcon ii = new ImageIcon(bi);

                    bg.setIcon(ii);
                    bg.setBounds(0, 0, width, height);
                } catch (IOException e) {
                    // Do nothing, have no background!
                }

                int usableWidth = width - 2 * (MARGIN_LEFTRIGHT);
                int columns = usableWidth / AlbumView.ALBUMVIEW_WIDTH;
                int remainingSpaceHoriz = usableWidth % AlbumView.ALBUMVIEW_WIDTH;

                int usableHeight = height - 2 * (MARGIN_TOPBOTTOM) - PlayBar.PLAYBAR_HEIGHT;
                int rows = usableHeight / AlbumView.ALBUMVIEW_HEIGHT;
                int remainingSpaceVert = usableHeight % AlbumView.ALBUMVIEW_HEIGHT;

                AlbumView jp;

                for (int i = 0; i < columns; i++) {
                    int leftPos = (i * AlbumView.ALBUMVIEW_WIDTH) + MARGIN_LEFTRIGHT + (remainingSpaceHoriz / 2);

                    for (int j = 0; j < rows; j++) {
                        int topPos = (j * AlbumView.ALBUMVIEW_HEIGHT) + MARGIN_TOPBOTTOM + (remainingSpaceVert / 2);

                        jp = new AlbumView();
                        jp.setBounds(leftPos, topPos, AlbumView.ALBUMVIEW_WIDTH, AlbumView.ALBUMVIEW_HEIGHT);
                        frame.add(jp);
                        panels.add(jp);

                    }
                }

                controller.setNumberOfAlbums(rows * columns);

                playBar.setBounds((width - PlayBar.PLAYBAR_WIDTH) / 2, height - PlayBar.PLAYBAR_HEIGHT, PlayBar.PLAYBAR_WIDTH, PlayBar.PLAYBAR_HEIGHT);
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
