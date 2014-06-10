package main.java.es.uclm.sri.sis.operaciones;

import java.util.ArrayList;

import de.umass.lastfm.Album;

public interface IRecomendador {
    
    public void analisisUsuario(String userName);
    
    public ArrayList<Object> getRecomendaciones();
    
    public Album[] getTopAlbumsDUsuario();
    
    public String[] getAvisosDSistema();
    
    public String[] getAvisosDUsuario();

}
