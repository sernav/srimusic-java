package main.java.es.uclm.sri.sis.fabricas;

import main.java.es.uclm.sri.sis.entidades.Album;
import main.java.es.uclm.sri.sis.entidades.AlbumPonderado;

/**
 * @author Sergio Navarro
 * */
public interface IFabricaDRecomendaciones {
    
    public AlbumPonderado ponderarAlbum(Album album);
    
    public AlbumPonderado ponderarAlbum(de.umass.lastfm.Album albumLastFm);

}
