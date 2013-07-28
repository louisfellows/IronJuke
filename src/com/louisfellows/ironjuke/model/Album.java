package com.louisfellows.ironjuke.model;
import java.util.HashMap;
import java.util.Random;

public class Album {
	public String m_Title;
	public String m_Artist;
	public HashMap<Integer, Track> m_Tracks;
	public String m_CoverLocation;
	
	public Album(String p_Title, String p_Artist, String p_CoverLocation) {
		m_Title = p_Title;
		m_Artist = p_Artist;
		m_CoverLocation = p_CoverLocation;
		
		m_Tracks = new HashMap<Integer, Track>();
	}

	public HashMap<Integer, Track> getTrack() {
		return m_Tracks;
	}

	public void setM_Track(HashMap<Integer, Track> p_Track) {
		this.m_Tracks = p_Track;
	}

	public String getTitle() {
		return m_Title;
	}

	public String getArtist() {
		return m_Artist;
	}
	
	public String getCover() {
		return m_CoverLocation;
	}
	
	public Track getTrack(Integer p_TrackNumber) throws Exception {
		if (m_Tracks.containsKey(p_TrackNumber)) {
			return m_Tracks.get(p_TrackNumber);
		}
		else
		{
			throw new Exception();
		}
	}
	
	public String getTrackList() {
		String trackStr = "";
		Integer counter = 1;
		for (Track track : m_Tracks.values()) {
			trackStr += counter + ". " + track.getTitle() + "<br>";
			counter++;
		}	
		return trackStr;
	}
	
	@Override
	public String toString() {
		String trackStr = "";
		for (Track track : m_Tracks.values()) {
			trackStr += track.getTitle() + ", ";
		}
		return m_Artist + " - " + m_Title + ": (" + trackStr + ")";
	}
	
	public Track getRandomTrack() {
		Random random = new Random();
		Track track = null;
		while (track == null) {
			int alNo = random.nextInt(m_Tracks.size());
			track = m_Tracks.get(alNo);
		}
		return track;
	}
}
