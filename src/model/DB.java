package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/*import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;*/


public class DB {

	private HashMap<Integer, Album> m_Albums;
	private File m_Root = new File("C:/Music");
	
	public DB() {
		m_Albums = new HashMap<Integer, Album>();
		
		try {
			String s = readFileAsString("./locationStr.txt");
			m_Root = new File( s );
		} catch (IOException e) {
		}
		
		recurseDirectory(m_Root);
	}
	
	public Album getAlbum(Integer p_AlbumNumber) throws Exception {
		if (m_Albums.containsKey(p_AlbumNumber)) {
			return m_Albums.get(p_AlbumNumber);
		}
		else
		{
			throw new Exception();
		}
	}
	
	private void recurseDirectory(File p_Directory) {
		
		if ( !p_Directory.isDirectory() ) {
			return;
		}
		
		if ( m_Albums.size() >= 999) {
			return;
		}
		
		FilenameFilter filefilter = new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				return name.endsWith(".mp3");
			}
		};
		    
		File[] MP3s = p_Directory.listFiles( filefilter );
		
		if ( MP3s.length > 0 ) {
			String[] details = p_Directory.getAbsolutePath().split("\\"+File.separator);
			if (details.length > 3) {
				String artist = details[details.length - 2];
				String title = details[details.length - 1];
				String cover;
				
				FilenameFilter imgfilter = new FilenameFilter() {
					
					public boolean accept(File dir, String name) {
						if (name.indexOf("Small") > 0) {
							return false;
						}
							
						if (name.endsWith(".jpg")) {
							return true;
						} else if (name.endsWith(".png")) {
							return true;
						} else if (name.endsWith(".bmp")) {
							return true;
						} else {
							return false;
						}
					}
				};
				    
				File[] imgs = p_Directory.listFiles( imgfilter );
				
				if ((new File(p_Directory.getAbsolutePath() + "cover.jpg")).exists()) { 
					cover = p_Directory.getAbsolutePath() + "cover.jpg";
					System.out.println(cover);
				} else if ((new File(p_Directory.getAbsolutePath() + "cover.png")).exists()) { 
					cover = p_Directory.getAbsolutePath() + "cover.png";
					System.out.println(cover);
				} else if ( imgs.length > 0 ) { 
					cover = imgs[0].getAbsolutePath();
					System.out.println(cover);
				} else { 
					cover = "noimg.png";
				} 
				
				Album album = new Album(title, artist, cover);
	
				for (File mp3 : MP3s) {
					/*try {
						MusicMetadataSet mmds = new MyID3().read(mp3);
						if (mmds == null) {
							throw new Exception();
						}
						
						MusicMetadata mmd = (MusicMetadata) mmds.getSimplified();
						Integer trackNo;
						String ttitle;
						
						if (mmd.getSongTitle() != null && mmd.getSongTitle().length() > 0){
							ttitle = mmd.getSongTitle();
						} else {
							ttitle = mp3.getName();
						}
						
						if (mmd.getTrackNumberFormatted() != null && mmd.getTrackNumberFormatted().length() > 0){
							trackNo = mmd.getTrackNumberNumeric().intValue();
						} else {
							Integer counter = 0;
							while( album.getTrack().containsKey(counter) ) {
									counter++;
							}
							trackNo = counter;
						}				
						
						Track track = new Track(ttitle, mp3.getAbsolutePath(), album);
						album.getTrack().put(trackNo, track);
						
					} catch (Exception e) {*/
						String ttitle = mp3.getName();
						Track track = new Track(ttitle, mp3.getAbsolutePath(), album);
						album.getTrack().put(album.getTrack().size(), track);
					//}
				}
				
				m_Albums.put(m_Albums.size(), album);
			}
		}
		
		FileFilter dirfilter = new FileFilter() {
			
			public boolean accept(File dir) {
				return dir.isDirectory();
			}
		};
		    
		List<File> dirs = Arrays.asList( p_Directory.listFiles(dirfilter) );
		
		Collections.sort(dirs);
			
		for (File dir : dirs) {
			recurseDirectory( dir );
		}
	}
	
	public int getNumberAlbums() {
		return m_Albums.size();
	}
	
	public Album getRandomAlbum() {
		Random random = new Random();
		Album album = null;
		while (album == null) {
			int alNo = random.nextInt(m_Albums.size());
			album = m_Albums.get(alNo);
		}
		return album;
	}
	
    private static String readFileAsString(String filePath)
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }
}
