package view;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.Controller;
import controller.KeyListen;

public class UI {
	
	private JFrame frame;
	private ArrayList<AlbumView> panels;
	private Controller m_Controller;
	private PlayBar m_PlayBar;
	
	public UI( Controller p_Controller ) {
		
		setController( p_Controller );
		
		panels = new ArrayList<AlbumView>();
		
		frame = new JFrame();
		frame.setLayout( null );
		
		frame.setUndecorated( true );
	    frame.setBounds(0,0,800,600);
				
		JLabel bg = new JLabel();
		ImageIcon ii = new ImageIcon("./bg.png");
		bg.setIcon(ii);
		bg.setBounds(0,0,800,600);
	    
		AlbumView jp;

		jp = new AlbumView();
		jp.setBounds(10,0,390,250);
		frame.add( jp );
		panels.add( jp );
		
		jp = new AlbumView();
		jp.setBounds(10,250,390,250);
		frame.add( jp );
		panels.add( jp );
		
		jp = new AlbumView();
		jp.setBounds(400,0,390,250);
		frame.add( jp );
		panels.add( jp );
		
		jp = new AlbumView();
		jp.setBounds(400,250,390,250);
		frame.add( jp );
		panels.add( jp );
		
		m_PlayBar = new PlayBar();
		m_PlayBar.setBounds(0,500,800,100);
		frame.add(m_PlayBar);
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListen( m_Controller ));
		
		frame.add(bg);
	}
	
	public void setController(Controller p_Controller) {
		m_Controller = p_Controller;
	}
	
	public PlayBar getPlayBar() {
		return m_PlayBar;
	}
	
	public void setUpAlbum(int p_Album, String p_Cover, String p_Artist, String p_Title, String p_Track, String p_AlNo) {
		AlbumView a = panels.get(p_Album);
		a.setVisible(true);
		a.setArtist(p_Artist);
		a.setTitle(p_Title);
		a.setCover(p_Cover);
		a.setTrackList(p_Track);
		a.setAlbumNo(p_AlNo);
	}
	
	public void hideAlbum(int p_Album) {
		AlbumView a = panels.get(p_Album);
		a.setVisible(false);
	}
	
	public void repaint() {
		//frame.repaint();
	}
}
