package com.louisfellows.ironjuke.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;

import com.louisfellows.ironjuke.controller.Controller;
import com.louisfellows.ironjuke.model.Track;



import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

public class Player extends PlaybackListener {
	
	private ArrayDeque<Track> m_Tracks;
	private AdvancedPlayer m_Player;
	private Boolean m_Playing;
	private Controller m_Controller;
	
	public Player() {
		
		m_Tracks = new ArrayDeque<Track>();
		m_Playing = false;
	}
	
	public void setController( Controller p_Controller ) {
		m_Controller = p_Controller;
	}
	
	public void playNext() {
		
		if (m_Tracks.size() > 0) {
			final Track track = m_Tracks.pollFirst();
			
			new Thread() {
				public void run() {
					try {
						m_Controller.updatePlayingTrack(track);
						FileInputStream fIS = new FileInputStream(track.m_Location);
						m_Player = new AdvancedPlayer(fIS);
						m_Player.setPlayBackListener(m_Controller.getPlayer());
						m_Playing = true;
						m_Player.play();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}	
				}
			}.start();
		}
	}
	
	public void addTrack(Track p_Track) {
		m_Tracks.addLast(p_Track);
		
		if (m_Playing == false) {
			playNext();
		}
	}
	
	public void stopCurrent() {
		m_Player.stop();
	}
	
	@Override
	public void playbackStarted(PlaybackEvent evt) {
	}
	
	@Override
	public void playbackFinished(PlaybackEvent evt) {
		m_Playing = false;
		m_Controller.clearPlayingTrack();
		playNext();
	}

}
