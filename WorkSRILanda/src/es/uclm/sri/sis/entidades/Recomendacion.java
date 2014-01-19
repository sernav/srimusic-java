package es.uclm.sri.sis.entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.persistencia.admon.AdmonAlbums;
import es.uclm.sri.persistencia.postgre.dao.model.Dalbums;

/**
 * 
 * @author Sergio Navarro
 * @since Jan, 2014
 */
public class Recomendacion {
    
    private Album album;
    
    private Date fecha;
    
    private String usuario;
    
    private boolean valido;
    
    public Recomendacion(Date fecha, String usuario) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.album = new Album();
        this.valido = true;
    }
    
    public Recomendacion(WekaSRIInstance ints, String usuario) {
        this.usuario = usuario;
        this.fecha = Calendar.getInstance().getTime();
        this.album = new Album();
        this.valido = true;
        
        AdmonAlbums admonAlbum = new AdmonAlbums();
        
        /*
         * Buscar album por t’tulo + artista. Insertar en la hash.
         */
        Dalbums dalbums[] = admonAlbum.devolverAlbums(ints.getTitulo(), ints.getArtita());
        if (dalbums.length > 0) {
            for (int j = 0; j < dalbums.length; j++) {
                Album album = new Album();
                album.setTitulo(dalbums[j].getTITUALBM());
                album.setArtista(dalbums[j].getAUTALBM());
                album.setFecha(null);
                album.setNumTemas(dalbums[j].getNUMEPIST());
                album.setPais("");
                
                ArrayList<String> etqAux = new ArrayList<String>();
                
                if (!Double.isNaN(ints.getAlternative()) && ints.getAlternative() > 0) {
                    etqAux.add("alternative");
                }
                if (!Double.isNaN(ints.getAmbient()) && ints.getAmbient() > 0) {
                    etqAux.add("ambient");
                }
                if (!Double.isNaN(ints.getBlues()) && ints.getBlues() > 0) {
                    etqAux.add("blues");
                }
                if (!Double.isNaN(ints.getBrit()) && ints.getBrit() > 0) {
                    etqAux.add("brit");
                }
                if (!Double.isNaN(ints.getClassic()) && ints.getClassic() > 0) {
                    etqAux.add("classic");
                }
                if (!Double.isNaN(ints.getElectronic()) && ints.getElectronic() > 0) {
                    etqAux.add("electronic");
                }
                if (!Double.isNaN(ints.getFolk()) && ints.getFolk() > 0) {
                    etqAux.add("folk");
                }
                if (!Double.isNaN(ints.getFunk()) && ints.getFunk() > 0) {
                    etqAux.add("funk");
                }
                if (!Double.isNaN(ints.getGrunge()) && ints.getGrunge() > 0) {
                    etqAux.add("grunge");
                }
                if (!Double.isNaN(ints.getHeavy()) && ints.getHeavy() > 0) {
                    etqAux.add("heavy");
                }
                if (!Double.isNaN(ints.getIndie()) && ints.getIndie() > 0) {
                    etqAux.add("indie");
                }
                if (!Double.isNaN(ints.getInstrumental()) && ints.getInstrumental() > 0) {
                    etqAux.add("instrumental");
                }
                if (!Double.isNaN(ints.getPop()) && ints.getPop() > 0) {
                    etqAux.add("pop");
                }
                if (!Double.isNaN(ints.getPunk()) && ints.getPunk() > 0) {
                    etqAux.add("punk");
                }
                if (!Double.isNaN(ints.getRap()) && ints.getRap() > 0) {
                    etqAux.add("rap");
                }
                if (!Double.isNaN(ints.getReggae()) && ints.getReggae() > 0) {
                    etqAux.add("reggae");
                }
                if (!Double.isNaN(ints.getRock()) && ints.getRock() > 0) {
                    etqAux.add("rock");
                }
                if (!Double.isNaN(ints.getSinger()) && ints.getSinger() > 0) {
                    etqAux.add("singer");
                }
                
                album.setEtiquetas(etqAux);
                
            }
        }
    }
    
    public Recomendacion() {
        this.fecha = Calendar.getInstance().getTime();
        this.album = new Album();
    }
    
    public Recomendacion(Album album) {
        this.fecha = Calendar.getInstance().getTime();
        this.album = album;
    }
    
    public Album getAlbum() {
        return album;
    }
    
    public void setAlbum(Album album) {
        this.album = album;
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
    
    public void setValido(boolean valido) {
        this.valido = valido;
    }
    
    public boolean getEsValido() {
        return valido;
    }
    
}
