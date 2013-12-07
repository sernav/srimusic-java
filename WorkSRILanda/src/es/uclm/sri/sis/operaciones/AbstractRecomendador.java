package es.uclm.sri.sis.operaciones;

import java.util.ArrayList;

import de.umass.lastfm.Album;
import es.uclm.sri.lastfm.IAnalisisLastfm;
import es.uclm.sri.lastfm.PlaybackDUsuario;

/**
 * 
 * @author Sergio Navarro
 * */
public abstract class AbstractRecomendador implements IRecomendador {
    
    private IAnalisisLastfm playback;

    public void analisisUsuario(String userName) {
        playback = new PlaybackDUsuario(userName);
        playback.run();
    }

    public ArrayList<Object> getRecomendaciones() {
        return null;
    }

    public String[] getAvisosDSistema() {
        return null;
    }

    public String[] getAvisosDUsuario() {
        return null;
    }

    public Album[] getTopAlbumsDUsuario() {
        return playback.getTopAlbumsUsuario();
    }

}
