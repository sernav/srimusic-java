package es.uclm.sri.sis.fabricas;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;

/**
 * @author Sergio Navarro
 * */
public interface IFabricaDRecomendaciones {
    
    public AlbumPonderado ponderarAlbum(Album album);
    
    public AlbumPonderado ponderarAlbum(de.umass.lastfm.Album albumLastFm);

}
