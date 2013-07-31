package com.louisfellows.ironjuke.controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Window Listener, Used to close the application if the main window is closed.
 * 
 * @author Louis Fellows <louis@louisfellows.com>
 * 
 */
public class WindowCloseListener implements WindowListener {

    @Override
    public void windowActivated(WindowEvent arg0) {
        // Do Nothing
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        // Do Nothing
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        System.exit(0);
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        // Do Nothing
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
        // Do Nothing
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
        // Do Nothing
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        // Do Nothing
    }

}
