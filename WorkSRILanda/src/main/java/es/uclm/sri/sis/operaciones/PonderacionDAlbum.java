package main.java.es.uclm.sri.sis.operaciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import main.java.es.uclm.sri.sis.entidades.Album;
import main.java.es.uclm.sri.sis.entidades.AlbumPonderado;
import main.java.es.uclm.sri.sis.entidades.Genero;
import main.java.es.uclm.sri.sis.log.Log;
import main.java.es.uclm.sri.sis.utilidades.FicheroDPropiedades;
import main.java.es.uclm.sri.sis.utilidades.UtilsDAlbum;

/**
 * Recoge los tags de cada uno de los discos y
 * los translada a géneros estandar del sistema. Calcula el peso de cada género
 * estandar para cada disco.
 * 
 * La suma de todos los pesos de un album es igual a 1.
 * 
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class PonderacionDAlbum implements IOperacion {

    private Album album;
    private de.umass.lastfm.Album albumLastfm;
    private AlbumPonderado albumPonderado;
    private boolean isAlbumLastfm;
    private static Collection<String> listaTags;
    private static Collection<Genero> listaGeneros;
    private static FicheroDPropiedades properties;

    private static final Logger logger = Logger.getLogger(PonderacionDAlbum.class);

    /**
     * Constructor desde la entidad Album del sistema
     * */
    public PonderacionDAlbum(Album album) {
        this.album = album;
        this.albumLastfm = null;
        this.isAlbumLastfm = false;

        this.listaTags = new ArrayList<String>();

        cargarGenerosEstandar();
    }

    /**
     * Constructor desde la entidad Album de Lastfm
     * */
    public PonderacionDAlbum(de.umass.lastfm.Album albumLastfm) {
        this.albumLastfm = albumLastfm;
        this.album = null;
        this.isAlbumLastfm = true;

        this.listaTags = new ArrayList<String>();

        cargarGenerosEstandar();
    }

    /**
     * Función que agrupa las operaciones para estandarizar y ponderar un album.
     * Primero se estandariza y después calcula sus pesos.
     * 
     * @param
     * @return AlbumPonderado
     * */
    public AlbumPonderado procesar() {
        try {
            Log.log("Ponderando tags y pesos de album", 1);
            this.listaTags = estandarizarTags();
            this.listaGeneros = convertirTags();

            calcularPesos();

            if (this.isAlbumLastfm) {
                this.album = new Album();
                this.album.setArtista(this.albumLastfm.getArtist());
                this.album.setTitulo(this.albumLastfm.getName());
                this.album.setFecha("");
                this.album.setPais("");
                //this.album.setNumTemas(this.albumLastfm.getTracks().size());
                this.album.setNumTemas(0);
            }

            crearAlbumPonderado();
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "(" + PonderacionDAlbum.class.getSimpleName() + ") Excepción General! Procesando " + e.getMessage());
        }

        return this.albumPonderado;
    }

    /**
     * Las instancias de <code>AlbumPonderado</code> extienden de la clase
     * <code>Album</code> y le agrega el vector de pesos ponderados. El vector
     * se costruye con los géneros estandarizados.
     * 
     * Da valor al atributo <code>albumPonderado</code> de this.
     * 
     * @param
     * @return
     * */
    protected void crearAlbumPonderado() throws Exception{
        Double[] pesosAlbum = null;
        this.albumPonderado = new AlbumPonderado(this.album, construirVectorDGeneros());
    }

    /**
     * Con la lista de géneros de estandarizados, la cual tiene el valor crea un
     * vector de valores.
     * 
     * @param
     * @return Double[]
     * */
    private Double[] construirVectorDGeneros() {
        ArrayList<String> listKeyProp = properties.getPropiedades();
        Double[] generos = new Double[18];
        iniciarVectorGeneros(generos);

        for (int i = 0; i < listKeyProp.size(); i++) {
            for (int j = 0; j < this.listaGeneros.size(); j++) {
                if (listKeyProp.get(i).equals(((ArrayList<Genero>) this.getListaGeneros()).get(j).getTipo())) {
                    Double valor = new Double(((ArrayList<Genero>) this.getListaGeneros()).get(j).getValorPonderado());
                    generos[i] = valor;
                }
            }
        }
        return generos;
    }

    /**
     * Inicializa el vector de doble presición a cero.
     * 
     * @param Double[]
     * @return Double[]
     * */
    private Double[] iniciarVectorGeneros(Double[] generos) {
        for (int i = 0; i < generos.length; i++) {
            Double cero = new Double(0);
            generos[i] = cero;
        }
        return generos;
    }

    /**
     * Recoge los tags de un album, bien desde el propio album, desde los tracks
     * del album o, en última instancia, del artista.
     * 
     * Descompone en tags simples y lo transforma en un tag estandar de la
     * aplicación.
     * 
     * @param
     * @return ArrayList<String>
     * */
    public ArrayList<String> estandarizarTags() throws Exception {
        Collection<String> tags = null;
        if (this.isAlbumLastfm) {
            tags = UtilsDAlbum.extraerTagsDAlbum(this.albumLastfm);
            //tags.addAll(UtilsDAlbum.extraerTagsDTracks(this.albumLastfm));
            tags.addAll(UtilsDAlbum.extraerTagsDArtista(this.albumLastfm));
        } else {
            tags = this.album.getEtiquetas();
        }
        ArrayList<String> listaTagsStnd = new ArrayList<String>();

        Iterator<String> it = tags.iterator();
        while (it.hasNext()) {
            String tag = it.next();
            if (UtilsDAlbum.isEtiquetaCompuesta(tag)) {
                String[] etqSimples = UtilsDAlbum.descomponerEtiquetaCompuesta(tag);
                for (int i = 0; i < etqSimples.length; i++) {
                    String etqStdr = estandarizaGeneroSimple(etqSimples[i]);
                    if (etqStdr != "") {
                        listaTagsStnd.add(etqStdr);
                    }
                }
            } else {
                String etqStdr = estandarizaGeneroSimple(tag);
                if (etqStdr != "") {
                    listaTagsStnd.add(etqStdr);
                }
            }
        }
        return listaTagsStnd;
    }

    /**
     * Estandariza un tag simple. Comprueba que el tag está en la lista de
     * géneros del archivo de propiedades y lo devuelve.
     * 
     * @param String
     * @return String
     * */
    private String estandarizaGeneroSimple(String tag) {
        String generoStdr = "";
        StringTokenizer crud = null;
        ArrayList<String> listKeyProp = new ArrayList<String>();
        listKeyProp = properties.getPropiedades();

        boolean isGeneroStd = false;

        for (int i = 0; i < listKeyProp.size() && !isGeneroStd; i++) {
            String valorProp = properties.getValorPropiedad(listKeyProp.get(i));
            crud = new StringTokenizer(valorProp, ",");
            if (isGeneroDListaEstandar(crud, tag)) {
                String alistKeyProp = listKeyProp.get(i);
                alistKeyProp.split("\\.");
                generoStdr = listKeyProp.get(i);
                isGeneroStd = true;
            }
        }
        return generoStdr;
    }

    /**
     * El tag está en los elementos de género.
     * 
     * @param StringTokenizer
     * @param String
     * @return boolean
     * */
    private static boolean isGeneroDListaEstandar(StringTokenizer crud, String tag) {
        while (crud.hasMoreElements()) {
            if (tag.equals(crud.nextToken())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera objetos de la entidad <code>Genero</code> desde los tag
     * estandarizados. No tiene en cuenta repeticiones.
     * 
     * @param
     * @return ArrayList<Genero>
     * */
    public ArrayList<Genero> convertirTags() throws Exception {
        ArrayList<Genero> listGeneros = new ArrayList<Genero>();
        HashMap<String, Genero> hashGeneros = new HashMap<String, Genero>();

        ArrayList<String> etiquetas = (ArrayList<String>) listaTags;
        Log.log("Estandarizando tags de album", 1);
        for (int i = 0; i < etiquetas.size(); i++) {
            if (!hashGeneros.containsKey(etiquetas.get(i))) {
                int numOcurGenero = 1;
                Genero genero = new Genero();
                genero.setTipo(etiquetas.get(i));
                for (int j = 0; j < etiquetas.size(); j++) {
                    if (i != j && !hashGeneros.containsKey(etiquetas.get(j))) {
                        if (genero.getTipo().equals(etiquetas.get(j))) {
                            numOcurGenero++;
                        }
                    }
                }
                genero.setNumOcurrencias(numOcurGenero);
                hashGeneros.put(genero.getTipo(), genero);
                listGeneros.add(genero);
                Log.log("-Tag#" + i + "= " + genero.getTipo() + " | " + genero.getNumOcurrencias() + " | " + genero.getValorPonderado(), 1);
            }
        }
        return listGeneros;
    }

    /**
     * Calcula los pesos de los géneros. El peso de cada género se recalcula en
     * función del número de géneros que tiene el album.
     * 
     * Ng = Número de géneros Na = Número apariciones de un género en el album
     * Pu = Peso unidad de aparición Pg = Peso de género
     * 
     * Pu = 1 / Ng Pg = Na * Pu
     * 
     * El valor se setea al género correspondiente.
     * 
     * @param
     * @return
     * @exception Exception
     * */
    public void calcularPesos() throws Exception {
        try {
            double numGenerosGlobal = numGenerosDAlbumGlobal((ArrayList<Genero>) this.listaGeneros);
            if (numGenerosGlobal > 0) {
                double pesoUnidad = 1 / numGenerosGlobal;

                for (int i = 0; i < this.listaGeneros.size(); i++) {
                    double numOcurr = ((ArrayList<Genero>) this.listaGeneros).get(i).getNumOcurrencias();
                    ((ArrayList<Genero>) this.listaGeneros).get(i).setValorPonderado(numOcurr * pesoUnidad);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.log(e, "(" + PonderacionDAlbum.class.getSimpleName() + ") Excepción Calculando Pesos" + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.log(e, "(" + PonderacionDAlbum.class.getSimpleName() + ") Excepción Calculando Pesos" + e.getMessage());
        }
    }

    /**
     * Número de ocurrencias de un género
     * 
     * @param ArrayList<Genero>
     * @return int
     * */
    private static int numGenerosDAlbumGlobal(ArrayList<Genero> generos) {
        int numGenerosGlobal = 0;

        for (int i = 0; i < generos.size(); i++) {
            numGenerosGlobal += generos.get(i).getNumOcurrencias();
        }

        return numGenerosGlobal;
    }

    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public de.umass.lastfm.Album getAlbumLastfm() {
        return this.albumLastfm;
    }

    public void setAlbumLastfm(de.umass.lastfm.Album albumLastfm) {
        this.albumLastfm = albumLastfm;
    }

    public AlbumPonderado getAlbumPonderado() {
        return this.albumPonderado;
    }

    public boolean isAlbumLastfm() {
        return isAlbumLastfm;
    }

    public Collection<String> getListaTags() {
        return listaTags;
    }

    public Collection<Genero> getListaGeneros() {
        return listaGeneros;
    }

    private void cargarGenerosEstandar() {
        try {
            properties = new FicheroDPropiedades("es/uclm/sri/recursos/generoEstandar.properties");
            properties.cargarPropiedades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ordenar(String tipoOrden) {

    }

    @Override
    public HashMap<Integer, String> getAvisosDSistema() {
        // TODO Auto-generated method stub
        return null;
    }

}
