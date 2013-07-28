package com.louisfellows.ironjuke.view;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.controller.KeyListen;

public class UI {

    private final JFrame frame;
    private final ArrayList<AlbumView> panels;
    private Controller controller;
    private final PlayBar playBar;

    public UI(final Controller newcontroller) {
        panels = new ArrayList<AlbumView>();
        frame = new JFrame();
        playBar = new PlayBar();
        setController(newcontroller);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

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

    public void repaint() {
        // frame.repaint();
    }
}
