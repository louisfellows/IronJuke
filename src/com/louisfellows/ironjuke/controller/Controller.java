package com.louisfellows.ironjuke.controller;

import com.louisfellows.ironjuke.model.Album;
import com.louisfellows.ironjuke.model.DB;
import com.louisfellows.ironjuke.model.Track;
import com.louisfellows.ironjuke.view.Player;
import com.louisfellows.ironjuke.view.UI;



public class Controller {
	private DB db;
	private Player player;
	private UI ui;
	private int albumStart;
	
	public Controller() {
		db = new DB();
		player = new Player();
		player.setController( this );

		ui = new UI( this );
		albumStart = 0;
		
		UpdateAlbums(albumStart);
	}
	
	public void UpdatePlayingTrack( Track p_Track ) {
		ui.getPlayBar().setTrack( p_Track.getTitle() );
		ui.getPlayBar().setAlbum( p_Track.getAlbum().getTitle() );
		ui.getPlayBar().setArtist( p_Track.getAlbum().getArtist() );
		
		ui.getPlayBar().setCover( p_Track.getAlbum().getCover() );
	}
	
	public void ClearPlayingTrack() {
		ui.getPlayBar().setTrack( "" );
		ui.getPlayBar().setAlbum( "" );
		ui.getPlayBar().setArtist( "" );
		
		ui.getPlayBar().setCover( "" );
	}
	
	public void TrackNumberEntered( Integer p_Album, Integer p_Track ) {
		try {
			
			Album a = db.getAlbum( p_Album );
			Track t = a.getTrack( p_Track );
			player.addTrack( t );
			
		} catch ( Exception e ) {
			ui.getPlayBar().setSelection("No Track");
		}
	}
	
	public void pageLeft(){
		albumStart -= 4;
		if (albumStart < 0) {
			albumStart = db.getNumberAlbums() - 4;
		}
		UpdateAlbums(albumStart);
	}
	
	public void pageRight(){
		albumStart += 4;
		if (albumStart > db.getNumberAlbums() - 4) {
			albumStart = 0;
		}
		UpdateAlbums(albumStart);
	}
	
	public void selUpdated( String p_SelStr ) {
		ui.getPlayBar().setSelection( p_SelStr );
	}
	
	public void UpdateAlbums( int startAlbum ) {
		try {
			Album a = db.getAlbum(startAlbum);
			String ii = a.getCover();
			ui.setUpAlbum( 	0,
							ii,
							a.getArtist(),
							a.getTitle(),
							a.getTrackList(),
							(startAlbum+1)+"");
		} catch (Exception e) {
			ui.hideAlbum( 0 );
		}
		try {
			Album a = db.getAlbum(startAlbum + 1);
			String ii = a.getCover();
			ui.setUpAlbum( 	1,
							ii,
							a.getArtist(),
							a.getTitle(),
							a.getTrackList(),
							(startAlbum+2)+"");
		} catch (Exception e) {
			ui.hideAlbum( 1 );
		}
		try {
			Album a = db.getAlbum(startAlbum + 2);
			String ii = a.getCover();
			ui.setUpAlbum( 	2,
							ii,
							a.getArtist(),
							a.getTitle(),
							a.getTrackList(),
							(startAlbum+3)+"");
		} catch (Exception e) {
			ui.hideAlbum( 2 );
		}
		try {
			Album a = db.getAlbum(startAlbum + 3);
			String ii = a.getCover();
			ui.setUpAlbum( 	3,
							ii,
							a.getArtist(),
							a.getTitle(),
							a.getTrackList(),
							(startAlbum+4)+"");
		} catch (Exception e) {
			ui.hideAlbum( 3 );
		}
		ui.repaint();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void addRandom() {
		Album album = db.getRandomAlbum();
		Track track = album.getRandomTrack();
		player.addTrack(track);
	}
	
	public void cancelCurrent() {
		player.stopCurrent();
	}
}
