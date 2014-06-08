package es.uclm.sri.sis.entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.sis.KSistema;

/**
 * Representa la recomendación compleja con todos los elementos.
 * 
 * @author Sergio Navarro
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
        this.album.setArtista(ints.getArtita());
        this.album.setTitulo(ints.getTitulo());
        this.album.setFecha(null);
        this.album.setNumTemas(0);
        this.album.setPais("");
        this.valido = true;

        ArrayList<String> etqAux = new ArrayList<String>();

        if (!Double.isNaN(ints.getAlternative()) && ints.getAlternative() > 0) {
            etqAux.add(KSistema.Generos.ALTERNATIVE);
        }
        if (!Double.isNaN(ints.getAmbient()) && ints.getAmbient() > 0) {
            etqAux.add(KSistema.Generos.AMBIENT);
        }
        if (!Double.isNaN(ints.getBlues()) && ints.getBlues() > 0) {
            etqAux.add(KSistema.Generos.BLUES);
        }
        if (!Double.isNaN(ints.getBrit()) && ints.getBrit() > 0) {
            etqAux.add(KSistema.Generos.BRIT);
        }
        if (!Double.isNaN(ints.getClassic()) && ints.getClassic() > 0) {
            etqAux.add(KSistema.Generos.CLASSIC);
        }
        if (!Double.isNaN(ints.getElectronic()) && ints.getElectronic() > 0) {
            etqAux.add(KSistema.Generos.ELECTRONIC);
        }
        if (!Double.isNaN(ints.getFolk()) && ints.getFolk() > 0) {
            etqAux.add(KSistema.Generos.FOLK);
        }
        if (!Double.isNaN(ints.getFunk()) && ints.getFunk() > 0) {
            etqAux.add(KSistema.Generos.FUNK);
        }
        if (!Double.isNaN(ints.getGrunge()) && ints.getGrunge() > 0) {
            etqAux.add(KSistema.Generos.GRUNGE);
        }
        if (!Double.isNaN(ints.getHeavy()) && ints.getHeavy() > 0) {
            etqAux.add(KSistema.Generos.HEAVY);
        }
        if (!Double.isNaN(ints.getIndie()) && ints.getIndie() > 0) {
            etqAux.add(KSistema.Generos.INDIE);
        }
        if (!Double.isNaN(ints.getInstrumental()) && ints.getInstrumental() > 0) {
            etqAux.add(KSistema.Generos.INSTRUMENTAL);
        }
        if (!Double.isNaN(ints.getPop()) && ints.getPop() > 0) {
            etqAux.add(KSistema.Generos.POP);
        }
        if (!Double.isNaN(ints.getPunk()) && ints.getPunk() > 0) {
            etqAux.add(KSistema.Generos.PUNK);
        }
        if (!Double.isNaN(ints.getRap()) && ints.getRap() > 0) {
            etqAux.add(KSistema.Generos.RAP);
        }
        if (!Double.isNaN(ints.getReggae()) && ints.getReggae() > 0) {
            etqAux.add(KSistema.Generos.REGGAE);
        }
        if (!Double.isNaN(ints.getRock()) && ints.getRock() > 0) {
            etqAux.add(KSistema.Generos.ROCK);
        }
        if (!Double.isNaN(ints.getSinger()) && ints.getSinger() > 0) {
            etqAux.add(KSistema.Generos.SINGER);
        }

        album.setEtiquetas(etqAux);

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
