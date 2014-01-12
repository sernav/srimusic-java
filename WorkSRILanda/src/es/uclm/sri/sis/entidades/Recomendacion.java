package es.uclm.sri.sis.entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.postgre.dao.model.Dalbums;

/**
 * 
 * @author Sergio Navarro
 * 
 * MODIFICAR PARA QUE SEA SÓLO UN ALBUM
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
    
    public Recomendacion(WekaSRIInstance[] ints, String usuario) {
        this.setFecha(Calendar.getInstance().getTime());
        this.setUsuario(usuario);
        albums = new HashMap<String, Album>();
        
        for (int i = 0; i < ints.length; i++) {
            AdmonAlbums admonAlbum = new AdmonAlbums();
            /*
             * Buscar album por título + artista.
             * Insertar en la hash.
             * */
            Dalbums dalbums[] = admonAlbum.devolverAlbums(ints[i].getTitulo(), ints[i].getArtita());
            if (dalbums.length > 0) {
                for (int j = 0; j < dalbums.length; j++) {
                    Album album = new Album();
                    album.setTitulo(dalbums[j].getTITUALBM());
                    album.setArtista(dalbums[j].getAUTALBM());
                    album.setFecha(null);
                    album.setNumTemas(dalbums[j].getNUMEPIST());
                    album.setPais("");
                    
                    ArrayList<String> etqAux = new ArrayList<String>();
                    
                    if (!Double.isNaN(ints[i].getAlternative()) && ints[i].getAlternative() > 0) {
                        etqAux.add("alternative");
                    }
                    if (!Double.isNaN(ints[i].getAmbient()) && ints[i].getAmbient() > 0) {
                        etqAux.add("ambient");
                    }
                    if (!Double.isNaN(ints[i].getBlues()) && ints[i].getBlues() > 0) {
                        etqAux.add("blues");
                    }
                    if (!Double.isNaN(ints[i].getBrit()) && ints[i].getBrit() > 0) {
                        etqAux.add("brit");
                    }
                    if (!Double.isNaN(ints[i].getClassic()) && ints[i].getClassic() > 0) {
                        etqAux.add("classic");
                    }
                    if (!Double.isNaN(ints[i].getElectronic()) && ints[i].getElectronic() > 0) {
                        etqAux.add("electronic");
                    }
                    if (!Double.isNaN(ints[i].getFolk()) && ints[i].getFolk() > 0) {
                        etqAux.add("folk");
                    }
                    if (!Double.isNaN(ints[i].getFunk()) && ints[i].getFunk() > 0) {
                        etqAux.add("funk");
                    }
                    if (!Double.isNaN(ints[i].getGrunge()) && ints[i].getGrunge() > 0) {
                        etqAux.add("grunge");
                    }
                    if (!Double.isNaN(ints[i].getHeavy()) && ints[i].getHeavy() > 0) {
                        etqAux.add("heavy");
                    }
                    if (!Double.isNaN(ints[i].getIndie()) && ints[i].getIndie() > 0) {
                        etqAux.add("indie");
                    }
                    if (!Double.isNaN(ints[i].getInstrumental()) && ints[i].getInstrumental() > 0) {
                        etqAux.add("instrumental");
                    }
                    if (!Double.isNaN(ints[i].getPop()) && ints[i].getPop() > 0) {
                        etqAux.add("pop");
                    }
                    if (!Double.isNaN(ints[i].getPunk()) && ints[i].getPunk() > 0) {
                        etqAux.add("punk");
                    }
                    if (!Double.isNaN(ints[i].getRap()) && ints[i].getRap() > 0) {
                        etqAux.add("rap");
                    }
                    if (!Double.isNaN(ints[i].getReggae()) && ints[i].getReggae() > 0) {
                        etqAux.add("reggae");
                    }
                    if (!Double.isNaN(ints[i].getRock()) && ints[i].getRock() > 0) {
                        etqAux.add("rock");
                    }
                    if (!Double.isNaN(ints[i].getSinger()) && ints[i].getSinger() > 0) {
                        etqAux.add("singer");
                    }
                    
                    album.setEtiquetas(etqAux);
                    
                    albums.put(album.getTitulo() + "#" + album.getArtista(), album);
                }
            }
        }
    }
    
    public Recomendacion() {
        albums = new HashMap<String, Album>();
    }
    
    public Recomendacion(Album album) {
        albums = new HashMap<String, Album>();
        albums.put(album.getTitulo() + "#" + album.getArtista(), album);
    }
    
    public void addAlbum(Album album) {
        albums.put(album.getTitulo() + "#" + album.getArtista(), album);
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
