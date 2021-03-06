package es.uclm.sri.sis.entidades;

import java.util.ArrayList;

/**
 * <code>Artista</code> es la clase que representa la entidad artista para Sri.Landa</br>
 * Diferenciamos esta entidad de la entidad Artist para la aplicación de Lastfm.
 * 
 * @author Sergio Navarro
 * */
public class Artista {

	private String nombre;
	private int numAlbums;
	private ArrayList<Album> albums;
	
	public Artista() {
		this.nombre = "default";
		this.numAlbums = -1;
		this.albums = new ArrayList<Album>();
		albums.add(new Album());
	}
	
	public Artista(String nombre, int numAlbums, ArrayList<Album> albums) {
		this.nombre = nombre;
		this.numAlbums = numAlbums;
		this.albums = albums;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getNumAlbums() {
		return numAlbums;
	}
	
	public void setNumAlbums(int numAlbums) {
		this.numAlbums = numAlbums;
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}
	
	public String toString() {
		return getNombre() + " - " + getAlbums().toString();
	}

}
