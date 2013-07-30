package com.louisfellows.ironjuke.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    JLabel trackList;
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

        trackList = new JLabel("TRACKS");
        trackList.setBounds(250, 40, 140, 210);
        trackList.setVerticalAlignment(JLabel.TOP);
        trackList.setOpaque(false);

        artist = new JLabel("ARTIST");
        artist.setBounds(250, 0, 140, 20);
        artist.setFont(new Font("Serif", Font.BOLD, 12));

        title = new JLabel("ALBUM");
        title.setBounds(250, 20, 140, 20);
        title.setFont(new Font("Serif", Font.BOLD, 12));

        albumNo = new JLabel("0");
        albumNo.setBounds(0, 0, 60, 40);
        albumNo.setOpaque(true);
        albumNo.setBackground(Color.BLACK);
        albumNo.setForeground(Color.WHITE);
        albumNo.setFont(new Font("Serif", Font.BOLD, 25));
        albumNo.setHorizontalAlignment(JLabel.CENTER);

        add(albumNo);
        add(title);
        add(artist);
        add(trackList);
        add(coverLabel);

    }

    /**
     * Sets the list of tracks to display. The track list is in HTML format, as
     * such, HTML tags are acceptable. Note, a BR tag should be used to separate
     * lines.
     * 
     * @param trackList
     *            the list of tracks to display
     */
    public void setTrackList(String trackList) {
        this.trackList.setText("<html>" + trackList + "</html>");
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
