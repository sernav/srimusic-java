package es.uclm.sri.sis.dataswap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import es.uclm.sri.sis.entidades.Album;

/**
 * @author Sergio Navarro
 * */
public class DSAlbum extends AbstractDataSwap implements IDataSwap, IDSAlbum {

    private Album album;
    private de.umass.lastfm.Album albumLastfm;
    
    private final String ORIGEN_ALBUM = "Lastfm";
    
    public DSAlbum(de.umass.lastfm.Album albumLastfm) {
        super();
        this.album = new Album();
    }
    
    public Album generarDSAlbum(de.umass.lastfm.Album album) {
        this.album.setTitulo(this.albumLastfm.getName());
        this.album.setArtista(this.albumLastfm.getArtist());
        this.album.setNumTemas(this.albumLastfm.getTracks().size());
        this.album.setFecha(null);
        incluirTagsDAlbum();
        return null;
    }
    
    private void incluirTagsDAlbum() {
        Collection<String> tags = this.albumLastfm.getTags();
        ArrayList<String> etiquetas = new ArrayList<String>();
        
        Iterator<String> it = tags.iterator();
        while(it.hasNext()) {
            etiquetas.add(it.next());
        }
        
        this.album.setEtiquetas(etiquetas);
    }
    
    public void generarDataSwap() {
        
    }

    public Album getAlbum() {
        return this.album;
    }
    
    public de.umass.lastfm.Album getAlbumDs() {
        return this.albumLastfm;
    }
    
    public void setAlbumDs(de.umass.lastfm.Album albumLastfm) {
        this.albumLastfm = albumLastfm;
    }

    public String getOrigenAlbum() {
        return ORIGEN_ALBUM;
    }

}
