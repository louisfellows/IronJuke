package com.louisfellows.ironjuke.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.controller.KeyListen;

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

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public PlayBar getPlayBar() {
        return playBar;
    }

    public void setUpAlbum(int album, String cover, String artist, String title, String track, String alNo) {
        AlbumView a = panels.get(album);
        a.setVisible(true);
        a.setArtist(artist);
        a.setTitle(title);
        a.setCover(cover);
        a.setTrackList(track);
        a.setAlbumNo(alNo);
    }

    public void hideAlbum(int album) {
        AlbumView a = panels.get(album);
        a.setVisible(false);
    }

    public void clearPlayingTrack() {
        playBar.clearPlayingTrack();
    }

    public void setPlaybarSelectionString(String selection) {
        playBar.setSelection(selection);
    }

    public void updatePlaybarTrack(String trackTitle, String albumTitle, String artist, String coverPath) {
        playBar.setTrack(trackTitle);
        playBar.setAlbum(albumTitle);
        playBar.setArtist(artist);

        playBar.setCover(coverPath);
    }

    public void repaint() {
        // frame.repaint();
    }
}
