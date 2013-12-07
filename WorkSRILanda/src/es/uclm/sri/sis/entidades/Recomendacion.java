package es.uclm.sri.sis.entidades;

import java.util.Date;
import java.util.HashMap;

/**
 * 
 * @author Sergio Navarro
 */
public class Recomendacion {
    
    private HashMap<String, Album> albums;
    private Date fecha;
    private String usuario;
    
    public Recomendacion(Date fecha, String usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
        albums = new HashMap<String, Album>();
    }
    
    public Recomendacion() {
        albums = new HashMap<String, Album>();
    }
    
    public Recomendacion(Album album) {
        albums = new HashMap<String, Album>();
        albums.put(album.getTitulo() + album.getArtista(), album);
    }
    
    public void addAlbum(Album album) {
        albums.put(album.getTitulo() + album.getArtista(), album);
    }
    
    public HashMap<String, Album> getAlbums() {
        return albums;
    }

    public void setAlbums(HashMap<String, Album> albums) {
        this.albums = albums;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
