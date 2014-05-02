package es.uclm.sri.sis.fabricas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import es.uclm.sri.clustering.ClustererSri;
import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.admon.AdmonPesosAlbum;
import es.uclm.sri.persistencia.postgre.dao.model.Dalbums;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.entidades.Recomendacion;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.operaciones.PonderacionDAlbum;

/**
 * <code>FabricaDRecomendaciones</code> es la clase que se encarga de crear recomendaciones
 * para un usuario concreto. Recoge las escuchas del usuario y genera los resultados, si las
 * esuchas del usuario no est‡n en el sistema, se ponderan y se incluyen para futuras
 * recomendaciones.
 * 
 * @author Sergio Navarro
 * */
public class FabricaDRecomendaciones 
        implements IFabricaDRecomendaciones, IFabrica {
    
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
    public void run() {
        Log.log("ÁFabrica de recomendaciones en marcha!", 1);
        
        AdmonAlbums admonAlbums = new AdmonAlbums();
        AdmonPesosAlbum admonPesos = new AdmonPesosAlbum();
        
        HashMap<String, Pesosalbum> hashAlbums = new HashMap<String, Pesosalbum>();
        try {
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
                Log.log("Procesando album recopilado del usuario " + a.getTitulo() + " # " + a.getArtista(), 1);
                
                /*
                 * Se buscar el album en la base de datos. Si no se encuentra, se
                 * inserta en la tabla de Albums.
                 * */
                Dalbums[] albumsApp = admonAlbums.devolverAlbums(albumsLastfm[i]);
                if (albumsApp.length == 0) {
                    admonAlbums.insertarAlbum(albumsLastfm[i]);
                }
                
                /*
                 * Buscamos en la tabla de albums ponderados.
                 * */
                Pesosalbum[] pesosAlbum = admonPesos.
                        devolverPesosAlbum(albumsLastfm[i].getName(), albumsLastfm[i].getArtist());
                if (pesosAlbum.length == 0) {
                    Log.log("New Album! T’tulo: " + a.getTitulo() + " Artista: " + a.getArtista(), 1);
                    /*
                     * 1. Ponderar album
                     * 2. Insertar en la tabla PESOSALBUM
                     * 3. Algoritmo de recomendaci—n
                     * */
                    PonderacionDAlbum pondera = new PonderacionDAlbum(albumsLastfm[i]);
                    AlbumPonderado albumPonderado = pondera.procesar();
                    Pesosalbum record;
                    try {
                        record = admonPesos.insertarPesosAlbum(albumPonderado);
                        hashAlbums.put(albumPonderado.getTitulo().trim() + "#" + albumPonderado.getArtista().trim(), record);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        Log.log(e, "(" + FabricaDRecomendaciones.class.getSimpleName() + ") Excepci—n SQL al insertar nuevo album! " + e.getMessage());
                    }
                } else if (!pesosAlbum[0].tienePesosValidos()) {
                    Log.log("El album (T’tulo: " + a.getTitulo() + " Artista: " + a.getArtista() + ") no tiene pesos v‡lidos", 2);
                    Log.log("Reprocesando album", 1);
                    PonderacionDAlbum pondera = new PonderacionDAlbum(albumsLastfm[i]);
                    AlbumPonderado albumPonderado = pondera.procesar();
                    Pesosalbum record = admonPesos.actualizarPesosAlbum(albumPonderado);
                    
                    hashAlbums.put(albumPonderado.getTitulo().trim() + "#" + albumPonderado.getArtista().trim(), record);
                } else {
                    Log.log("El album (T’tulo: " + a.getTitulo() + " Artista: " + a.getArtista() + ") ya est‡ ponderado y en la BD: " + pesosAlbum[0].getID_PESOSALBUM(), 1);
                    hashAlbums.put(pesosAlbum[0].getALBUM().trim() + "#" + pesosAlbum[0].getARTISTA().trim(), pesosAlbum[0]);
                }
            }
            Log.log("ÁInvocando a la f‡brica de Usuarios!", 1);
            FabricaDUsuarios makeupUser = new FabricaDUsuarios(playback.getNickUsuario(), hashAlbums, true);
            makeupUser.run();
            Pesosusuario pesosUser = makeupUser.getPesosManufactura();
            /*
             * Invocar clusterer (Singleton)
             * */
            ClustererSri clusterer = ClustererSri.getInstance();
            try {
                /*
                 * Aqu’ el vector de gŽneros del usuario, una vez se ha actualizado con las escuchas
                 * recientes
                 * */
                WekaSRIInstance inst = new WekaSRIInstance(pesosUser.getSINGER(), pesosUser.getRAP(), pesosUser.getAMBIENT(),
                        pesosUser.getINDIE(), pesosUser.getBLUES(), pesosUser.getREGGAE(), pesosUser.getPUNK(),
                        pesosUser.getHEAVY(), pesosUser.getALTERNATIVE(), pesosUser.getCLASSIC(), pesosUser.getELECTRONIC(),
                        pesosUser.getROCK(), pesosUser.getPOP(), pesosUser.getBRIT(), pesosUser.getFOLK(), pesosUser.getFUNK(),
                        pesosUser.getINSTRUMENTAL(), pesosUser.getGRUNGE());
                WekaSRIInstance[] wekaInst = clusterer.generarRecomendacionesWeka(inst);
                /*
                 * Aqu’ las recomendaciones
                 * */
                Recomendacion recomendacion = new Recomendacion(wekaInst[0], usuario);
            } catch (Exception e) {
                avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                        "Error al generar recomendaciones de clusterer");
                e.printStackTrace();
            }
            
        } else {
            avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                    "No hay escuchas de usuario de Last.fm");
        }
        
        } catch (Exception e) {
            
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

    public HashMap<Integer, String> getAvisosDSistema() {
        return null;
    }

    @Override
    public void aplicarSistemaDReglas() {
        // TODO Auto-generated method stub
        
    }

}

