package es.uclm.sri.parches.calculadoras;

import java.util.ArrayList;
import java.util.HashMap;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.entidades.Genero;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;

public class CalculaPesoGenerosDAlbum {
	
	private static String origProperties = "src/es/uclm/sri/properties/generoEstandar.properties";
	private static FicheroDPropiedades properties;
	protected static ArrayList<Genero> generosDAlbum = new ArrayList<Genero>();
	protected static ArrayList<AlbumPonderado> listAlbumPonderados = new ArrayList<AlbumPonderado>();
	
	public static void main(String[] args) {
		ArrayList<Album> listAlbums = procesarDatosIN("/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Norm.csv");
		procesoDCalculo(listAlbums);
	}
	
	protected static void procesoDCalculo(ArrayList<Album> listAlbums) {
		int numAlbum = 0;
		for (Album album : listAlbums) {
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
		TratarCSVAlbum.generarCSVAlbumPonderado(listAlbumPonderados, properties.getPropiedades(), 18, "/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Ponderados_v2.csv");
	}
	
	protected static ArrayList<Album> procesarDatosIN(String path) {
		ArrayList<Album> listAlbums = TratarCSVAlbum.leerCVSAlbums(path);
		return listAlbums;
	}
	
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
	
	private static void ponderarGeneros() {

		double numGenerosGlobal = numGenerosDAlbumGlobal(generosDAlbum);
		if (numGenerosGlobal > 0) {
			double pesoUnidad = 1/numGenerosGlobal;
			
			for(int i=0; i < generosDAlbum.size(); i++) {
				double numOcurr = generosDAlbum.get(i).getNumOcurrencias();
				generosDAlbum.get(i).setValorPonderado(numOcurr*pesoUnidad);
			}
		}
	}
	
	private static Double[] construirVectorDGeneros() {
		properties = new FicheroDPropiedades(origProperties);
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

}
