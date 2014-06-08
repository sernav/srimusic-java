package es.uclm.sri.sis.procesos;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.procesos.csv.TratarCSVAlbum;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;
import es.uclm.sri.sis.utilidades.UtilsDAlbum;

/**
 * Desde un fichero CSV en el que están almacenados los albums, se estandarizan los tags que traen
 * por los géneros del sistema. El resueltado también se almacena en un fichero csv.
 * 
 * @author Sergio Navarro
 * */
public class EstandarizarGenerosDAlbum {
	
	private static String destinyPath;
	private static String csvAlbums;
	
	private static FicheroDPropiedades properties;
	
	private static final Logger logger = Logger.getLogger(EstandarizarGenerosDAlbum.class);
	
	/**
	 * Constructor con el csv de albums y la ruta de destino.
	 * */
	public EstandarizarGenerosDAlbum(String csvAlbums, String destinyPath) {
		this.csvAlbums = csvAlbums;
		this.destinyPath = destinyPath;
	}
	
	/**
	 * Función principal para la transformación de géneros de album.
	 * Recoge cada uno de los registros del fichero csv que representa un album e invoca a la 
	 * función <code>estandarizarGeneros</code>.
	 * Una vez los géneros de un album están estandarizados, se almacena en un ArrayList.
	 * Cuando todos los albums han sido tratados, se llama a la clase <code>TratarCSVAlbum#generarCSVAlbums</code>
	 * donde se genera el nuevo fichero con formato csv.
	 * */
	public void generarAlbumsEstandar() {
		ArrayList<Album> listaAlbums = TratarCSVAlbum.leerCVSAlbums(getCsvAlbums());
		ArrayList<Album> listaAlbumsNormalizado = new ArrayList<Album>();
		try {
			// Lista de géneros del sistema
			properties = new FicheroDPropiedades(KSistema.Recursos.PATH_GENEROS_ESTANDAR_PROPERTIES);
			properties.cargarPropiedades();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int index = 1; index < listaAlbums.size(); index++) {
			Album album = listaAlbums.get(index);
			album.setEtiquetas(estandarizarGeneros(album));
			album.setPais("");
			Log.log("Album Normalizado: " + album.toString(), 1);
			listaAlbumsNormalizado.add(album);
		}
		TratarCSVAlbum.generarCSVAlbums(listaAlbumsNormalizado, 16, destinyPath, logger);
	}
	
	/**
	 * Recoge las etiquetas de una album y devuelven su equivalente en etiqueta del sistema.
	 * Varias etiquetas de album se pueden corresponder con una mismta etiqueta estandar, en 
	 * tal caso, dicha etiqueta, tendrá tantas ocurrencias como correspondencias se den.
	 * 
	 * @param album: Album
	 * @return ArrayList<String>
	 * */
	protected ArrayList<String> estandarizarGeneros(Album album) {
		ArrayList<String> listaEtq = album.getEtiquetas();
		ArrayList<String> listaEtqSimple = new ArrayList<String>();
		for (int i=0; i < listaEtq.size(); i++) {
			/*
			 * Se identifican etiquetas compuestas, como p.e.: indietrónica.
			 * */
			if(UtilsDAlbum.isEtiquetaCompuesta(listaEtq.get(i))) {
				String[] etqSimples = UtilsDAlbum.descomponerEtiquetaCompuesta(listaEtq.get(i));
				for (int j  =0; j < etqSimples.length; j++) {
					String etqStandar = estandarizaGeneroSimple(etqSimples[j]);
					if (etqStandar != "")
						listaEtqSimple.add(etqStandar);
				}
			} else {
				String etqStandar = estandarizaGeneroSimple(listaEtq.get(i));
				if (etqStandar != "")
					listaEtqSimple.add(etqStandar);
			}
		}
		return listaEtqSimple;
	}
	
	/**
	 * Calcula el género estandarizado para un género de album
	 * 
	 * @param género
	 * @return género estándar
	 * */
	private String estandarizaGeneroSimple(String genero) {
		String generoStdr = "";
		StringTokenizer crud = null;
		ArrayList<String> listKeyProp = new ArrayList<String>();
		listKeyProp = properties.getPropiedades();
		
		boolean isGeneroStd = false;
		
		for(int i=0; i < listKeyProp.size() && !isGeneroStd; i++) {
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
	
	/**
	 * Comprueba si el género está entre los géneros estandarizables
	 * 
	 * @param elemento de la lista de géneros estándar.
	 * @param genero
	 * @return es o no género de la lista
	 * */
	private boolean isGeneroDListaEstandar(StringTokenizer crud, String genero) {
		while(crud.hasMoreElements()) {
			if(genero.equals(crud.nextToken())) {
				return true;
			}
		}
		return false;
	}
	
	public String getDestinyPath() {
		return this.destinyPath;
	}
	
	public String getCsvAlbums() {
		return this.csvAlbums;
	}
	
	public String getCsvResultados() {
		return getDestinyPath();
	}

}