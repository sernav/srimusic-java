package es.uclm.sri.sis.dataswap;

import es.uclm.sri.sis.entidades.Album;


/**
 * @author Sergio Navarro
 * */
public interface IDSAlbum {
    
    public Album generarDSAlbum(de.umass.lastfm.Album album);
    
    public String getOrigenAlbum();

}
