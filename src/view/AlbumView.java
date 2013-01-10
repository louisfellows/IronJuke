package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlbumView extends JPanel{
	
	private static final long serialVersionUID = -2024743821441955507L;

	JLabel trackList;
	ImageIcon cover;
	JLabel title;
	JLabel artist;
	JLabel albumNo;
	JLabel coverLabel;
	
	public AlbumView() {
		
		setLayout( null );
		setOpaque(false);
		
		cover = new ImageIcon();
		coverLabel = new JLabel( cover );
		coverLabel.setBounds(0, 0, 250, 250);
		
		trackList = new JLabel( "TRACKS" );
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

	public void setTrackList(String trackList) {
		this.trackList.setText( "<html>" + trackList + "</html>" );
	}

	public void setCover(String cover) {
		try {
			BufferedImage bi = ImageIO.read(new File(cover));
			bi = resizeImage(bi, bi.getType());
			this.cover = new ImageIcon( bi );
			coverLabel.setIcon(this.cover);		
		} catch (IOException e) {
			coverLabel.setIcon(null);
		}
	}

	public void setArtist( String artist ) {
		this.artist.setText( artist );
	}
	
	public void setTitle( String title ) {
		this.title.setText( title );
	}
	
	public void setAlbumNo( String alNo ) {
		while (alNo.length() < 3) {
			alNo = "0" + alNo;
		}
		this.albumNo.setText( alNo );
	}
	
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
    	BufferedImage resizedImage = new BufferedImage(200, 200, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.drawImage(originalImage, 0, 0, 200, 200, null);
    	g.dispose();
     
    	return resizedImage;
    }

	
}
