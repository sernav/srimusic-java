package es.uclm.sri.sis.operaciones;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.admon.AdmonPesosAlbum;
import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;

/**
 * @author Sergio Navarro
 * */
public class FabricaDRecomendaciones 
        implements IFabricaDRecomendaciones {
    
    private HashMap<String, Album> albums;
    private de.umass.lastfm.Album[] albumsLastFm;
    private String usuario;
    private PlaybackDUsuario playback;
    
    private HashMap<Integer, String> avisosDSistema;
    
    private static SqlSessionFactory sqlMapper;
    private static SqlSession session = null;
    PesosalbumMapper mapper = null;

    public FabricaDRecomendaciones(PlaybackDUsuario playback) {
        this.albums = new HashMap<String, Album>();;
        this.playback = playback;
        
        avisosDSistema = new HashMap<Integer, String>();
    }
    
    public FabricaDRecomendaciones() {
        this.albums = new HashMap<String, Album>();
        this.playback = null;
        
        avisosDSistema = new HashMap<Integer, String>();
    }
    
    public void procesarDatos() {
        this.mapper = session.getMapper(PesosalbumMapper.class);
        
        AdmonAlbums admonAlbums = new AdmonAlbums();
        AdmonPesosAlbum admonPesos = new AdmonPesosAlbum();
        
        if (this.playback != null) {
            
            de.umass.lastfm.Album[] albumsLastfm = this.playback.getTopAlbumsUsuario();
            for (int i = 0; i < albumsLastfm.length; i++) {
                /*
                 * Creamos una nueva instancia de Album con la que se ir‡ trabajando
                 * en el proceso.
                 * */
                Album a = new Album(albumsLastfm[i].getName(), 
                        albumsLastfm[i].getArtist(), null, 
                        albumsLastfm[i].getReleaseDate().toString(), "",
                        albumsLastfm[i].getTracks().size());
                albums.put(a.getTitulo() + "#" + a.getArtista(), a);
                
                Pesosalbum[] pesosAlbum = admonPesos.
                        devolverPesosAlbum(albumsLastfm[i].getName(), albumsLastfm[i].getArtist());
                
                Albumsapp[] albumsApp = admonAlbums.devolverAlbums(albumsLastfm[i]);
                if (albumsApp.length == 0) {
                    admonAlbums.insertarAlbum(albumsLastfm[i]);
                }
                
                /*
                 * Buscamos en la tabla de albums ponderados.
                 * > Si est‡, buscamos en la tabla de albums de la aplicaci—n.
                 * > Si no est‡ lo insertamos.
                 * 
                 * */
                if (pesosAlbum.length > 0) {
                    /*
                     * El album ya est‡ ponderado.
                     * Lo buscamos en la tabla de albums de la apliaci—n
                     * */
                } else {
                    /*
                     * 1. Ponderar album
                     * 2. Insertar en la tabla PESOSALBUM
                     * 3. Algoritmo de recomendaci—n
                     * */
                    
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
    
    private static void establecerConexion() {
        sqlMapper = ConnectionFactory.getSession();
    }
}

