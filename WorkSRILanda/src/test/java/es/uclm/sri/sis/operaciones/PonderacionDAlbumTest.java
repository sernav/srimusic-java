package es.uclm.sri.sis.operaciones;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.junit.BeforeClass;
import org.junit.Test;

import de.umass.lastfm.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.entidades.Genero;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;
import es.uclm.sri.sis.utilidades.UtilsDAlbum;

public class PonderacionDAlbumTest {
	
	private static final String API_KEY_LASTFM = "58169c7f645f6ad54529f3012548fc3a";
	
	private static FicheroDPropiedades properties;
	private static Album albumLastfm;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		cargarGenerosEstandar();
		assertNotNull("Generos estandar cargados", properties);
		
		albumLastfm = Album.getInfo("Radiohead", "OK Computer", API_KEY_LASTFM);
	}

	@Test
	public void testEstandarizarTags() {
		Collection<String> tags = null;
		tags = UtilsDAlbum.extraerTagsDAlbum(this.albumLastfm);
		tags.addAll(UtilsDAlbum.extraerTagsDArtista(this.albumLastfm));
		
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
        assertNotNull("Tags estandarizados", listaTagsStnd);
	}
	
	@Test
	public void testConvertirTags() {
		ArrayList<String> listaTags = new ArrayList<String>();
		listaTags.add("generoEstandar.alternative");
		listaTags.add("generoEstandar.rock");
		listaTags.add("generoEstandar.alternative");
		listaTags.add("generoEstandar.indie");
		listaTags.add("generoEstandar.electronic");
		ArrayList<Genero> listGeneros = new ArrayList<Genero>();
        HashMap<String, Genero> hashGeneros = new HashMap<String, Genero>();

        ArrayList<String> etiquetas = (ArrayList<String>) listaTags;
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
            }
        }
        assertNotNull("Tags convertidos", listGeneros);
	}
	
	@Test
	public void testCalcularPesos() {
		Collection<Genero> listaGeneros = new ArrayList<Genero>();
		listaGeneros.add(new Genero("generoEstandar.alternative", 3, 0.0));
		listaGeneros.add(new Genero("generoEstandar.rock", 1, 0.0));
		listaGeneros.add(new Genero("generoEstandar.indie", 1, 0.0));
		listaGeneros.add(new Genero("generoEstandar.electronic", 1, 0.0));
		double numGenerosGlobal = numGenerosDAlbumGlobal((ArrayList<Genero>) listaGeneros);
        if (numGenerosGlobal > 0) {
            double pesoUnidad = 1 / numGenerosGlobal;

            for (int i = 0; i < listaGeneros.size(); i++) {
                double numOcurr = ((ArrayList<Genero>) listaGeneros).get(i).getNumOcurrencias();
                ((ArrayList<Genero>) listaGeneros).get(i).setValorPonderado(numOcurr * pesoUnidad);
            }
        }
        assertNotNull("Cálculo", listaGeneros);
	}
	
	@Test
	public void testCrearAlbumPonderado() {
		Double[] pesosAlbum = null;
		Collection<Genero> listaGeneros = new ArrayList<Genero>();
		listaGeneros.add(new Genero("generoEstandar.alternative", 3, 0.0));
		listaGeneros.add(new Genero("generoEstandar.rock", 1, 0.0));
		listaGeneros.add(new Genero("generoEstandar.indie", 1, 0.0));
		listaGeneros.add(new Genero("generoEstandar.electronic", 1, 0.0));
		es.uclm.sri.sis.entidades.Album album = new es.uclm.sri.sis.entidades.Album();
        album.setArtista(this.albumLastfm.getArtist());
        album.setTitulo(this.albumLastfm.getName());
        album.setFecha("");
        album.setPais("");
        album.setNumTemas(0);
		AlbumPonderado albumPonderado = new AlbumPonderado(album, construirVectorDGeneros(listaGeneros));
		
		assertNotNull("Nuevo album ponderado", albumPonderado);
	}
	
	private static void cargarGenerosEstandar() {
        try {
            properties = new FicheroDPropiedades("generoEstandar.properties");
            properties.cargarPropiedades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	// ************************************************
	//          Funciones de PonderacionDAlbum
	// ************************************************
	
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
	
	private static boolean isGeneroDListaEstandar(StringTokenizer crud, String tag) {
        while (crud.hasMoreElements()) {
            if (tag.equals(crud.nextToken())) {
                return true;
            }
        }
        return false;
    }
	
	private static int numGenerosDAlbumGlobal(ArrayList<Genero> generos) {
        int numGenerosGlobal = 0;

        for (int i = 0; i < generos.size(); i++) {
            numGenerosGlobal += generos.get(i).getNumOcurrencias();
        }

        return numGenerosGlobal;
    }
	
	private Double[] construirVectorDGeneros(Collection<Genero> listaGeneros) {
        ArrayList<String> listKeyProp = properties.getPropiedades();
        Double[] generos = new Double[18];
        iniciarVectorGeneros(generos);

        for (int i = 0; i < listKeyProp.size(); i++) {
            for (int j = 0; j < listaGeneros.size(); j++) {
                if (listKeyProp.get(i).equals(((ArrayList<Genero>) listaGeneros).get(j).getTipo())) {
                    Double valor = new Double(((ArrayList<Genero>) listaGeneros).get(j).getValorPonderado());
                    generos[i] = valor;
                }
            }
        }
        return generos;
    }
	
	private Double[] iniciarVectorGeneros(Double[] generos) {
        for (int i = 0; i < generos.length; i++) {
            Double cero = new Double(0);
            generos[i] = cero;
        }
        return generos;
    }

}
