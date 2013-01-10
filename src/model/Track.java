package model;

public class Track {
	public String m_Title;
	public String m_Location;
	public Album m_Album;
	
	public Track(String p_Title, String p_Location, Album p_Album) {
		
		p_Title = p_Title.substring(0, p_Title.length() - 4 ); //remove .mp3
		p_Title = p_Title.replaceFirst("[0-9]*[\\s|\\-]*", "");
		
		m_Title = p_Title;	
		m_Location = p_Location;
		m_Album = p_Album;
	}

	public String getTitle() {
		return m_Title;
	}

	public String getLocation() {
		return m_Location;
	}

	public Album getAlbum() {
		return m_Album;
	}	
}
