package com.louisfellows.ironjuke.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.louisfellows.ironjuke.view.util.ImageHandler;

/**
 * Panel which contains the currently playing song and the number the user is
 * typing to select a song.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class PlayBar extends JPanel {

    private static final long serialVersionUID = 5040392197822450873L;

    private final JLabel track;
    private final JLabel artist;
    private final JLabel album;
    private final JLabel selection;
    private final JLabel image;

    /**
     * Setup the bar.
     */
    public PlayBar() {

        setLayout(null);
        setOpaque(false);

        track = new JLabel("");
        track.setFont(new Font("Serif", Font.BOLD, 25));

        artist = new JLabel("");
        artist.setFont(new Font("Serif", Font.BOLD, 25));

        album = new JLabel("");
        album.setFont(new Font("Serif", Font.BOLD, 25));

        selection = new JLabel("   :  ");
        image = new JLabel();

        add(image);
        image.setBounds(0, 0, 100, 100);
        image.setOpaque(false);

        JPanel details = new JPanel();
        details.setLayout(new GridLayout(3, 1));
        details.setOpaque(false);

        details.add(track);
        details.add(artist);
        details.add(album);

        add(details);
        details.setBounds(110, 0, 500, 100);

        selection.setForeground(Color.black);
        selection.setFont(new Font("Serif", Font.BOLD, 48));
        selection.setHorizontalAlignment(JLabel.CENTER);

        add(selection);
        selection.setBounds(600, 0, 200, 100);
    }

    /**
     * Sets the track string for the currently playing song display
     * 
     * @param trackText
     *            the new track name
     */
    public void setTrack(String trackText) {
        track.setText(trackText);
    }

    /**
     * Sets the artist string for the currently playing song display
     * 
     * @param artistText
     *            the new artist name
     */
    public void setArtist(String artistText) {
        artist.setText(artistText);
    }

    /**
     * Sets the album string for the currently playing song display
     * 
     * @param albumText
     *            the new album name
     */
    public void setAlbum(String albumText) {
        album.setText(albumText);
    }

    /**
     * Sets the cover image path for the currently playing song display
     * 
     * @param coverPath
     *            the new cover image path
     */
    public void setCover(String coverPath) {
        try {
            BufferedImage bi = ImageIO.read(new File(coverPath));
            bi = ImageHandler.resizeImage(bi, bi.getType(), 100, 100);
            ImageIcon cover = new ImageIcon(bi);
            image.setIcon(cover);
        } catch (IOException e) {
            image.setIcon(null);
        }
    }

    /**
     * Sets the song selection string.
     * 
     * @param selectionString
     *            the new song selection string
     */
    public void setSelection(String selectionString) {
        selection.setText(selectionString);
    }

    /**
     * Clear the text in the currently playing track bar
     */
    public void clearPlayingTrack() {
        setTrack("");
        setAlbum("");
        setArtist("");

        setCover("");
    }
}
