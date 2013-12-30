package es.uclm.sri.sis.operaciones;

import java.util.ArrayList;
import java.util.HashMap;

import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.admon.AdmonPesosAlbum;
import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.ponderacion.PonderacionDAlbum;

/**
 * <code>FabricaDRecomendaciones</code> es la clase que se encarga de crear recomendaciones
 * para un usuario concreto. Recoge las escuchas del usuario y genera los resultados, si las
 * esuchas del usuario no est‡n en el sistema, se ponderan y se incluyen para futuras
 * recomendaciones.
 * 
 * @author Sergio Navarro
 * */
public class FabricaDRecomendaciones 
        implements IFabricaDRecomendaciones {
    
    private HashMap<String, Album> albums;
    private de.umass.lastfm.Album[] albumsLastFm;
    private String usuario;
    private PlaybackDUsuario playback;
    
    private HashMap<Integer, String> avisosDSistema;

    public FabricaDRecomendaciones(PlaybackDUsuario playback) {
        this.albums = new HashMap<String, Album>();
        this.playback = playback;
        
        avisosDSistema = new HashMap<Integer, String>();
    }
    
    public FabricaDRecomendaciones() {
        this.albums = new HashMap<String, Album>();
        this.playback = null;
        
        avisosDSistema = new HashMap<Integer, String>();
    }
    
    /**
     * Recoge el objeto <code>PlaybackDUsuario</code> con las escuchas de usuario
     * para comprobar, primero si est‡n en el modelo de datos, ponderar el album
     * si no est‡ en la base de datos e invocar el algoritmo de recomendaciones.
     * 
     * @param
     * @return
     * */
    public void procesarDatos() {
        
        AdmonAlbums admonAlbums = new AdmonAlbums();
        AdmonPesosAlbum admonPesos = new AdmonPesosAlbum();
        
        if (this.playback != null) {
            de.umass.lastfm.Album[] albumsLastfm = this.playback.getTopAlbumsUsuario();
            
            for (int i = 0; i < albumsLastfm.length; i++) {
                /*
                 * Creamos una nueva instancia de Album para procesar
                 * */
                Album a = new Album(albumsLastfm[i].getName(), 
                        albumsLastfm[i].getArtist(), null, 
                        albumsLastfm[i].getReleaseDate() != null ? albumsLastfm[i].getReleaseDate().toString() : "", "",
                        albumsLastfm[i].getTracks().size());
                albums.put(a.getTitulo() + "#" + a.getArtista(), a);
                
                /*
                 * Se buscar el album en la base de datos. Si no se encuentra, se
                 * inserta en la tabla de Albums.
                 * */
                Albumsapp[] albumsApp = admonAlbums.devolverAlbums(albumsLastfm[i]);
                if (albumsApp.length == 0) {
                    admonAlbums.insertarAlbum(albumsLastfm[i]);
                }
                
                /*
                 * Buscamos en la tabla de albums ponderados.
                 * */
                Pesosalbum[] pesosAlbum = admonPesos.
                        devolverPesosAlbum(albumsLastfm[i].getName(), albumsLastfm[i].getArtist());
                if (pesosAlbum.length > 0) {
                    /*
                     * El album ya est‡ ponderado. Invocar el algoritmo de recomendaci—n.
                     * */
                } else {
                    /*
                     * 1. Ponderar album
                     * 2. Insertar en la tabla PESOSALBUM
                     * 3. Algoritmo de recomendaci—n
                     * */
                    PonderacionDAlbum pondera = new PonderacionDAlbum(albumsLastfm[i]);
                    AlbumPonderado albumPonderado = pondera.procesar();
                    admonPesos.insertarPesosAlbum(albumPonderado);
                    
                }
            }
        } else {
            avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                    "No hay escuchas de usuario de Last.fm");
        }
        
    }

    public ArrayList<Object> getRecomendaciones() {
        return null;
    }

    public void guardarDatos() {
        
    }

    public AlbumPonderado ponderarAlbum(Album album) {
        return null;
    }

    public AlbumPonderado ponderarAlbum(de.umass.lastfm.Album albumLastFm) {
        return null;
    }

    public String[] getAvisosDSistema() {
        return null;
    }

    @Override
    public void aplicarSistemaDReglas() {
        // TODO Auto-generated method stub
        
    }
}

