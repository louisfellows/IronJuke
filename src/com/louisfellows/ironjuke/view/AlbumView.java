package com.louisfellows.ironjuke.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.louisfellows.ironjuke.view.util.ImageHandler;

/**
 * Displays an album.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class AlbumView extends JPanel {

    private static final long serialVersionUID = -2024743821441955507L;

    private static final int TRACKS_PER_ALBUM = 13;
    public static final int ALBUMVIEW_WIDTH = 390;
    public static final int ALBUMVIEW_HEIGHT = 250;

    ArrayList<JLabel> trackList;
    ImageIcon cover;
    JLabel title;
    JLabel artist;
    JLabel albumNo;
    JLabel coverLabel;

    public AlbumView() {

        setLayout(null);
        setOpaque(false);

        cover = new ImageIcon();
        coverLabel = new JLabel(cover);
        coverLabel.setBounds(0, 0, 250, 250);

        artist = new JLabel("ARTIST");
        artist.setBounds(250, 0, 140, 20);
        artist.setFont(new Font("SansSerif", Font.BOLD, 12));
        artist.setForeground(Color.BLACK);

        title = new JLabel("ALBUM");
        title.setBounds(250, 20, 140, 20);
        title.setFont(new Font("SansSerif", Font.BOLD, 12));
        title.setForeground(Color.BLACK);

        albumNo = new JLabel("0");
        albumNo.setBounds(0, 0, 60, 40);
        albumNo.setOpaque(true);
        albumNo.setBackground(Color.BLACK);
        albumNo.setForeground(Color.WHITE);
        albumNo.setFont(new Font("SansSerif", Font.BOLD, 25));
        albumNo.setHorizontalAlignment(JLabel.CENTER);

        add(albumNo);
        add(title);
        add(artist);
        add(coverLabel);

        trackList = new ArrayList<JLabel>();
        for (int i = 0; i < TRACKS_PER_ALBUM; i++) {
            JLabel track = new JLabel("TRACK" + i);
            track.setBounds(260, 40 + (i * 15), 130, 15);
            track.setFont(new Font("SansSerif", Font.PLAIN, 10));
            track.setVerticalAlignment(JLabel.TOP);
            track.setOpaque(false);
            track.setForeground(Color.BLACK);
            trackList.add(track);
            add(track);
        }

    }

    /**
     * Sets the list of tracks to display. The track list is in HTML format, as
     * such, HTML tags are acceptable. Note, a BR tag should be used to separate
     * lines.
     * 
     * @param trackList
     *            the list of tracks to display
     */
    public void setTrackList(HashMap<Integer, String> tracks) {
        for (Integer i : tracks.keySet()) {
            this.trackList.get(i).setText((i + 1) + " " + tracks.get(i));
            this.trackList.get(i).setVisible(true);
        }
        for (int i = tracks.size(); i < TRACKS_PER_ALBUM; i++) {
            this.trackList.get(i).setVisible(false);
        }
    }

    /**
     * Sets the Image to be displayed as the cover.
     * 
     * @param cover
     *            the path to the image to display.
     */
    public void setCover(String cover) {
        try {
            BufferedImage bi = ImageIO.read(new File(cover));
            bi = ImageHandler.resizeImage(bi, bi.getType(), 200, 200);
            this.cover = new ImageIcon(bi);
            coverLabel.setIcon(this.cover);
        } catch (IOException e) {
            coverLabel.setIcon(null);
        }
    }

    /**
     * Sets the name of the artist to display
     * 
     * @param artist
     *            The name of the artist
     */
    public void setArtist(String artist) {
        this.artist.setText(artist);
    }

    /**
     * Sets the title of the album to display
     * 
     * @param title
     *            The title of the album
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * Sets the display number of the album.
     * 
     * @param alNo
     *            the display number of the album
     */
    public void setAlbumNo(String alNo) {
        while (alNo.length() < 3) {
            alNo = "0" + alNo;
        }
        this.albumNo.setText(alNo);
    }
}
