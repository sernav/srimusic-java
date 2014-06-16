package test.java.es.uclm.sri.lastfm;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class PlaybackDUsuarioTest {
	
	private static final String API_KEY = "58169c7f645f6ad54529f3012548fc3a";
    private static final String SECRET = "d99785f1e3ee9305b7c67b6e81bce5b1";
	
	private Session sesion;
	private User usuario;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testSession() {
		String token = Authenticator.getToken(API_KEY);
        sesion = Session.createSession(API_KEY, SECRET, token);
        
        assertNotNull("Sesion LastFm obtenida", sesion);
	}
	
	@Test
	public void testAlbums() {
		PaginatedResult<Track> tracksResult = usuario.getRecentTracks("djAguadilla", API_KEY);
        Collection<Track> tracks = tracksResult.getPageResults();
        
        assertNotNull("El usuario tiene escuchas", tracks);
        
	}

}
