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
	
	private JLabel m_Track;
	private JLabel m_Artist;
	private JLabel m_Album;
	private JLabel m_Selection;
	private JLabel m_Image;
	
	public PlayBar() {
		
		setLayout(null);
		setOpaque(false);
		
		m_Track = new JLabel("");
		m_Track.setFont(new Font("Serif", Font.BOLD, 25));
		
		m_Artist = new JLabel("");
		m_Artist.setFont(new Font("Serif", Font.BOLD, 25));
		
		m_Album = new JLabel("");
		m_Album.setFont(new Font("Serif", Font.BOLD, 25));
		
		m_Selection = new JLabel("   :  ");
		m_Image = new JLabel();
		
		add(m_Image);
		m_Image.setBounds(0,0,100,100);
		m_Image.setOpaque(false);
		
		JPanel details = new JPanel();
		details.setLayout( new GridLayout( 3, 1 ) );
		details.setOpaque(false);
		
		details.add( m_Track );
		details.add( m_Artist );
		details.add( m_Album );
		
		add(details);
		details.setBounds(110,0,500,100);
		
		m_Selection.setForeground(Color.black);
		m_Selection.setFont(new Font("Serif", Font.BOLD, 48));
		m_Selection.setHorizontalAlignment(JLabel.CENTER);
		
		add(m_Selection);
		m_Selection.setBounds(600, 0, 200, 100);
	}
	
	public void setTrack( String p_Track ) {
		m_Track.setText( p_Track );
	}
	
	public void setArtist( String p_Artist ) {
		m_Artist.setText( p_Artist );
	}
	
	public void setAlbum( String p_Album ) {
		m_Album.setText( p_Album );
	}
	
	public void setCover( String p_Cover ) {
		try {
			BufferedImage bi = ImageIO.read(new File(p_Cover));
			bi = resizeImage(bi, bi.getType());
			ImageIcon cover = new ImageIcon( bi );
			m_Image.setIcon(cover);		
		} catch (IOException e) {
			m_Image.setIcon(null);
		}
	}
	
	public void setSelection( String p_Sel ) {
		m_Selection.setText( p_Sel );
	}
	
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
    	BufferedImage resizedImage = new BufferedImage(100, 100, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, 100, 100, null);
    	g.dispose();
     
    	return resizedImage;
    }
}
