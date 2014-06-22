package es.uclm.sri.lastfm;

import de.umass.lastfm.Album;

/**
 *
 * @author Sergio Navarro
 * */
public interface IAnalisisLastfm {
    
    public void setNickUsuario(String nick);
    
    public void run();
    
    public String autenticarUsuario();
    
    public Album[] getTopAlbumsUsuario();
    
    public String getDatosUsuario();

}
