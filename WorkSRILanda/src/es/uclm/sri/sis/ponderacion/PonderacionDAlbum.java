package es.uclm.sri.sis.ponderacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.Genero;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;
import es.uclm.sri.sis.utilidades.UtilsDAlbum;

/**
 * 
 * @author Sergio Navarro
 * */
public class PonderacionDAlbum implements IPonderacion {
    
    private Album album;
    private de.umass.lastfm.Album albumLastfm;
    private boolean isAlbumLastfm;
    
    private static FicheroDPropiedades properties;
    private static final Logger logger = Logger.getLogger(PonderacionDAlbum.class);
    
    private final String GENEROS_STD = "src/es/uclm/sri/generoEstandar.properties";
    
    public PonderacionDAlbum(Album album) {
        this.album = album;
        this.albumLastfm = null;
        this.isAlbumLastfm = false;
    }
    
    public PonderacionDAlbum(de.umass.lastfm.Album albumLastfm) {
        this.albumLastfm = albumLastfm;
        this.album = null;
        this.isAlbumLastfm = true;
    }

    public void procesar() {
        
    }

    public void calcularPesos() {
        
    }
    
    public ArrayList<String> estandarizarTags() {
        Collection<String> tags = this.albumLastfm.getTags();
        ArrayList<String> listaEtqSimple = new ArrayList<String>();
        
        Iterator<String> it = tags.iterator();
        while(it.hasNext()) {
            String tag = it.next();
            if (UtilsDAlbum.isEtiquetaCompuesta(tag)) {
                String[] etqSimples = UtilsDAlbum.descomponerEtiquetaCompuesta(tag);
                for (int i = 0; i < etqSimples.length; i++) {
                    String etqStdr = estandarizaGeneroSimple(etqSimples[i]);
                    if (etqStdr != "") {
                        listaEtqSimple.add(etqStdr);
                    }
                }
            } else {
                String etqStdr = estandarizaGeneroSimple(tag);
                if (etqStdr != "") {
                    listaEtqSimple.add(etqStdr);
                }
            }
        }
        return listaEtqSimple;
    }
    
    private static String estandarizaGeneroSimple(String genero) {
        String generoStdr = "";
        StringTokenizer crud = null;
        ArrayList<String> listKeyProp = new ArrayList<String>();
        listKeyProp = properties.getPropiedades();
        
        boolean isGeneroStd = false;
        
        for(int i = 0; i < listKeyProp.size() && !isGeneroStd; i++) {
            String valorProp = properties.getValorPropiedad(listKeyProp.get(i));
            crud = new StringTokenizer(valorProp, ",");
            if (isGeneroDListaEstandar(crud, genero)) {
                String alistKeyProp = listKeyProp.get(i);
                alistKeyProp.split(".");
                generoStdr = listKeyProp.get(i);
                isGeneroStd = true;
            }
        }
        return generoStdr;
    }
    
    private static boolean isGeneroDListaEstandar(StringTokenizer crud, String genero) {
        while(crud.hasMoreElements()) {
            if(genero.equals(crud.nextToken())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Genero> convertirTags() {
        ArrayList<Genero> listGeneros = new ArrayList<Genero>();
        HashMap<String, Genero> hashGeneros = new HashMap<String, Genero>();
        Collection<String> tags = albumLastfm.getTags();
        
        Iterator<String> it = tags.iterator();
        while (it.hasNext()) {
            String tag = it.next();
            if (!hashGeneros.containsKey(tag)) {
                Genero genero = new Genero();
                genero.setTipo(tag);
                genero.setNumOcurrencias(1);
                
                hashGeneros.put(genero.getTipo(), genero);
                listGeneros.add(genero);
            }
        }
        
        return listGeneros;
    }
    
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public de.umass.lastfm.Album getAlbumLastfm() {
        return albumLastfm;
    }

    public void setAlbumLastfm(de.umass.lastfm.Album albumLastfm) {
        this.albumLastfm = albumLastfm;
    }

    public boolean isAlbumLastfm() {
        return isAlbumLastfm;
    }
    
    private void cargarGenerosEstandar() {
        try {
            properties = new FicheroDPropiedades(GENEROS_STD);
            properties.cargarPropiedades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ordenar(String tipoOrden) {
        
    }

}
