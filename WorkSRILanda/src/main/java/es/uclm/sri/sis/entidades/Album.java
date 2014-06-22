package es.uclm.sri.sis.entidades;

import java.util.ArrayList;

/**
 * <code>Album</code> es la clase que representa la entidad disco para Sri.Landa
 * 
 * @author Sergio Navarro
 * */
public class Album {
	
	private String titulo;
	private String artista;
	private ArrayList<String> etiquetas;
	private String fecha;
	private String pais;
	private int numTemas;

	public Album(String titulo, String artista, ArrayList<String> etiquetas, String fecha, 
			String pais, int numTemas) {
		this.titulo = titulo;
		this.artista = artista;
		this.etiquetas = etiquetas;
		this.fecha = fecha;
		this.pais = pais;
		this.numTemas = numTemas;
	}
	
	public Album() {
		this.titulo = "default";
		this.artista = "default";
		this.etiquetas = new ArrayList<String>();
		this.fecha = "01/01/2000";
		this.pais = "default";
		this.numTemas = 0;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public ArrayList<String> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(ArrayList<String> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public int getNumTemas() {
		return numTemas;
	}

	public void setNumTemas(int numTemas) {
		this.numTemas = numTemas;
	}

	public String toString() {
		return getTitulo() + " - " + getArtista() + " - " + getEtiquetas().toString();
	}
}
