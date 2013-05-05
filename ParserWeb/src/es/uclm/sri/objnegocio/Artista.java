package es.uclm.sri.objnegocio;

import java.util.ArrayList;

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

}
