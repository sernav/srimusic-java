package es.uclm.sri.objnegocio;

import java.util.ArrayList;

public class Album {
	
	private String titulo;
	private String artista;
	private ArrayList<String> etiquetas;
	private int anyo;

	public Album(String titulo, String artista, ArrayList<String> etiquetas, int anyo) {
		this.titulo = titulo;
		this.artista = artista;
		this.etiquetas = etiquetas;
		this.anyo = anyo;
	}
	
	public Album() {
		this.titulo = "default";
		this.artista = "default";
		this.etiquetas = new ArrayList<String>();
		this.anyo = 1900;
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

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	
	

}
