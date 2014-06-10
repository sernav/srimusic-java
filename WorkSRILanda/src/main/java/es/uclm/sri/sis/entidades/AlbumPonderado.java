package main.java.es.uclm.sri.sis.entidades;

import java.util.ArrayList;

/**
 * <code>AlbumPonderado</code> clase que extiende de la clase de entidad Album con la
 * particularidad de que tiene sus pesos ponderados para realizar el clustering.
 * 
 * @author Sergio Navarro
 * */
public class AlbumPonderado extends Album {
	
	private Double[] pesosGeneros;
	
	public AlbumPonderado() {
		super();
	}
	
	public AlbumPonderado(String titulo, String artista, ArrayList<String> etiquetas, String fecha,
			String pais, int numTemas) {
		super(titulo, artista, etiquetas, fecha, pais, numTemas);
	}
	
	public AlbumPonderado(Album album, Double[] pesosAlbum) {
		super(album.getTitulo(), album.getArtista(), album.getEtiquetas(), album.getFecha(),
				album.getPais(), album.getNumTemas());
		this.pesosGeneros = pesosAlbum;
	}
	
	public AlbumPonderado(Double[] pesosGeneros) {
		super();
		this.pesosGeneros = pesosGeneros;
	}
	
	public Double[] getPesosGeneros() {
		return pesosGeneros;
	}
	
	public void setGeneros(Double[] pesosGeneros) {
		this.pesosGeneros = pesosGeneros;
	}
	
	public String toString() {
		return getTitulo() + " - " + getArtista() + " - " + getPesosGeneros().toString();
	}

}
