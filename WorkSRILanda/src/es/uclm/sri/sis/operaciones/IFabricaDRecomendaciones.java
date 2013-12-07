package es.uclm.sri.sis.operaciones;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;

/**
 * 
 * @author Sergio Navarro
 * */
public interface IFabricaDRecomendaciones {
    
    public void procesarDatos();
    
    public void guardarDatos();
    
    public void aplicarSistemaDReglas();
    
    public AlbumPonderado ponderarAlbum(Album album);
    
    public AlbumPonderado ponderarAlbum(de.umass.lastfm.Album albumLastFm);
    
    public String[] getAvisosDSistema();

}
