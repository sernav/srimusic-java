package es.uclm.sri.sis.operaciones;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.clustering.weka.WekaDatosCluster;
import es.uclm.sri.lastfm.IAnalisisLastfm;
import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.admon.AdmonPesosAlbum;
import es.uclm.sri.persistencia.postgre.dao.AlbumsappMapper;
import es.uclm.sri.persistencia.postgre.dao.PesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;

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
        try {
            establecerConexion();
            session = sqlMapper.openSession();
            session.getConnection().setAutoCommit(true);
            
            this.mapper = session.getMapper(PesosalbumMapper.class);
            
            AdmonAlbums admonAlbums = new AdmonAlbums();
            AdmonPesosAlbum admonPesos = new AdmonPesosAlbum();
            
            if (this.playback != null) {
                de.umass.lastfm.Album[] albumsLastfm = this.playback.getTopAlbumsUsuario();
                for (int i = 0; i < albumsLastfm.length; i++) {
                    
                    Album a = new Album(albumsLastfm[i].getName(), 
                            albumsLastfm[i].getArtist(), null, 
                            albumsLastfm[i].getReleaseDate().toString(), "",
                            albumsLastfm[i].getTracks().size());
                    albums.put(a.getTitulo() + "#" + a.getArtista(), a);
                    
                    Pesosalbum[] pesosAlbum = admonPesos.
                            devolverPesosAlbum(albumsLastfm[i].getName(), albumsLastfm[i].getArtist());
                    if (pesosAlbum.length > 0) {
                        /*
                         * El album ya est‡ ponderado.
                         * 1. Insertar en ALBUMSAPP
                         * */
                        admonAlbums.insertarAlbum(albumsLastfm[i]);
                    } else {
                        /*
                         * 0. Insertar album para la aplicaci—n
                         * */
                        admonAlbums.insertarAlbum(albumsLastfm[i]);
                        
                        /*
                         * 1. Ponderar album
                         * */
                        
                        // 2. Insertar en la tabla PESOSALBUM
                        // 3. Insertar en la tabla ALBUMSAPP
                        
                        // Algoritmo de recomendaci—n
                    }
                }
            } else {
                avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                        "No hay escuchas de usuario de Last.fm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

