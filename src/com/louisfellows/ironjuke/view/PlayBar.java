package com.louisfellows.ironjuke.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayBar extends JPanel {

    private static final long serialVersionUID = 5040392197822450873L;

    private final JLabel track;
    private final JLabel artist;
    private final JLabel album;
    private final JLabel selection;
    private final JLabel image;

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

    public void setTrack(String p_Track) {
        track.setText(p_Track);
    }

    public void setArtist(String p_Artist) {
        artist.setText(p_Artist);
    }

    public void setAlbum(String p_Album) {
        album.setText(p_Album);
    }

    public void setCover(String p_Cover) {
        try {
            BufferedImage bi = ImageIO.read(new File(p_Cover));
            bi = resizeImage(bi, bi.getType());
            ImageIcon cover = new ImageIcon(bi);
            image.setIcon(cover);
        } catch (IOException e) {
            image.setIcon(null);
        }
    }

    public void setSelection(String p_Sel) {
        selection.setText(p_Sel);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(100, 100, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 100, 100, null);
        g.dispose();

        return resizedImage;
    }

    public void clearPlayingTrack() {
        setTrack("");
        setAlbum("");
        setArtist("");

        setCover("");
    }
}
