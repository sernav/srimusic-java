package es.uclm.sri.lastfm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import de.umass.lastfm.Album;
import de.umass.lastfm.Authenticator;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

/**
 * 
 * La clase <code>PlaybackDUsuario</code> recoge los discos reproducidos recientemente por 
 * un usuario de la aplicación web LastFm. Se utiliza el proyecto lastfm-java que se 
 * comunica con la aplicación LastFm mediante los servicios web de su API.
 * 
 * @author Sergio Navarro
 * */
public class PlaybackDUsuario implements IAnalisisLastfm {
    
    private static final String API_KEY = "58169c7f645f6ad54529f3012548fc3a";
    private static final String SECRET = "d99785f1e3ee9305b7c67b6e81bce5b1";
    
    private User usuario;
    private Session sesion;
    
    private String nickUser;
    private HashMap<String, Album> hashAlbums;
    
    public PlaybackDUsuario(String nickUser) {
        this.nickUser = nickUser;
        this.hashAlbums = new HashMap<String, Album>();
    }
    
    /**
     * Inicia la sesión de usuario mediante la key de LastFm para Sri.Landa y su clave
     * secreta.
     * 
     * Obtiene las reproducciones de un usuario concreto y, a través de éstas, sus albums
     * recientes más escuchados.
     * 
     * @param
     * @return
     * 
     * */
    public void run() {
        String token = Authenticator.getToken(API_KEY);
        this.sesion = Session.createSession(API_KEY, SECRET, token);

        PaginatedResult<Track> tracksResult = usuario.getRecentTracks(nickUser, API_KEY);
        Collection<Track> tracks = tracksResult.getPageResults();
        
        Iterator<Track> it = tracks.iterator();
        while(it.hasNext()) {
            Track track = it.next(); 
            String albumOrMbid = track.getAlbumMbid();
            if (albumOrMbid == null || albumOrMbid.length() == 0) {
                albumOrMbid = track.getAlbum();
            }
            Album albumDTrack = Album.getInfo(track.getArtist(), albumOrMbid, API_KEY);
            if (albumDTrack != null) {
                this.hashAlbums.put(albumDTrack.getName(), albumDTrack);
            }
        }

    }

    public void setNickUsuario(String nick) {
        this.nickUser = nick;
    }

    public String autenticarUsuario() {
        return null;
    }
    
    /**
     * Del hashmap de Album al array
     * 
     * @param
     * @return o[] Album
     * */
    public Album[] getTopAlbumsUsuario() {
        return hashAlbums.values().toArray(new Album[hashAlbums.size()]);
    }
    
    public Session getSession() {
        return this.sesion;
    }
    
    public User getUsuarioLastfm() {
        return this.usuario;
    }
    
    public String getUsuario() {
        return this.usuario.getName();
    }
    
    public String getNickUsuario() {
        return this.nickUser;
    }

    public String getDatosUsuario() {
        return User.getInfo(this.nickUser, API_KEY).toString();
    }

}
