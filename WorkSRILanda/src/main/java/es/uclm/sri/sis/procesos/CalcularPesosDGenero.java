package es.uclm.sri.sis.procesos;

import java.util.ArrayList;
import java.util.HashMap;

import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.entidades.Genero;
import es.uclm.sri.sis.procesos.csv.TratarCSVAlbum;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;

/**
 * Calcula los pesos del global de los géneros para cada uno de los albums.
 * 
 * @author Sergio Navarro
 * */
public class CalcularPesosDGenero {
	
	private static FicheroDPropiedades properties;
	private static ArrayList<Genero> generosDAlbum = new ArrayList<Genero>();
	private static ArrayList<AlbumPonderado> listAlbumPonderados = new ArrayList<AlbumPonderado>();
	private static ArrayList<Album> listAlbums;
	
	private static String destinoCsv;
	
	/**
	 * Constructor con la ruta del fichero csv.
	 * */
	public CalcularPesosDGenero(String pathCsv, String destinoCsv) {
		this.destinoCsv = destinoCsv;
		this.listAlbums = procesarDatosIN(pathCsv);
	}
	
	/**
	 * Para cada uno de los albums, invoca a la lógica de cálculo de pesos
	 * */
	public void procesoDCalculo() {
		int numAlbum = 0;
		for (Album album : this.listAlbums) {
			if (numAlbum > 0) {
				generosDAlbum = conversorEtiquetasAGeneros(album);
				ponderarGeneros();
				Double[] generosPonderados = construirVectorDGeneros();
				AlbumPonderado albumStndr = new AlbumPonderado(album, generosPonderados);
				//albumStndr.setGeneros(generosPonderados);
				listAlbumPonderados.add(albumStndr);
			}
			numAlbum++;
		}
		TratarCSVAlbum.generarCSVAlbumPonderado(listAlbumPonderados, properties.getPropiedades(), 18, destinoCsv);
	}
	
	/**
	 * Recoge registros de un fichero CSV con albums, y genera registros de la
	 * entidad Album.
	 * 
	 * @param ruta del fichero csv
	 * @return ArrayList<Album>
	 * */
	protected ArrayList<Album> procesarDatosIN(String path) {
		ArrayList<Album> listAlbums = TratarCSVAlbum.leerCVSAlbums(path);
		return listAlbums;
	}
	
	/**
	 * Calcula las ocurrencias de etiquetas estandar por album y genera una
	 * instancia de la clase <code>Genero</code>
	 * 
	 * @param album
	 * @return ArrayList<Genero>
	 * */
	private static ArrayList<Genero> conversorEtiquetasAGeneros(Album album) {
		ArrayList<Genero> listGeneros = new ArrayList<Genero>();
		HashMap<String, Genero> hashGeneros = new HashMap<String, Genero>();
		ArrayList<String> etiquetas = album.getEtiquetas();
		for (int i=0; i < etiquetas.size(); i++) {
			if (!hashGeneros.containsKey(etiquetas.get(i))) {
				int numOcurGenero = 1;
				Genero genero = new Genero();
				genero.setTipo(etiquetas.get(i));
				for (int j=0; j < etiquetas.size(); j++) {
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
     * */
	private static void ponderarGeneros() {
		double numGenerosGlobal = numGenerosDAlbumGlobal(generosDAlbum);
		if (numGenerosGlobal > 0) {
			double pesoUnidad = 1 / numGenerosGlobal;
			
			for(int i=0; i < generosDAlbum.size(); i++) {
				double numOcurr = generosDAlbum.get(i).getNumOcurrencias();
				generosDAlbum.get(i).setValorPonderado(numOcurr * pesoUnidad);
			}
		}
	}
	
	/**
	 * Genera el vector con cada uno de los elementos (géneros)
	 * */
	private static Double[] construirVectorDGeneros() {
		properties = new FicheroDPropiedades(KSistema.Recursos.PATH_GENEROS_ESTANDAR_PROPERTIES);
		properties.cargarPropiedades();
		ArrayList<String> listKeyProp = properties.getPropiedades();
		
		Double[] generos = new Double[18];
		iniciarVectorGeneros(generos);
		
		for(int i=0; i < listKeyProp.size(); i++) {
			for (int j=0; j < generosDAlbum.size(); j++) {
				if (listKeyProp.get(i).equals(generosDAlbum.get(j).getTipo())) {
					Double valor = new Double(generosDAlbum.get(j).getValorPonderado());
					generos[i] = valor;
				}
			}
		}
		return generos;
	}
	
	private static int numGenerosDAlbumGlobal(ArrayList<Genero> generos) {
		int numGenerosGlobal = 0;
		
		for (int i=0; i < generos.size(); i++) {
			numGenerosGlobal += generos.get(i).getNumOcurrencias();
		}
		
		return numGenerosGlobal;
	}
	
	private static Double[] iniciarVectorGeneros(Double[] generos) {
		for(int i=0; i < generos.length; i++) {
			Double cero = new Double(0);
			generos[i] = cero;
		}
		return generos;
	}
	
	public ArrayList<Album> getListaAlbumsOrigen() {
		return this.listAlbums;
	}

}
