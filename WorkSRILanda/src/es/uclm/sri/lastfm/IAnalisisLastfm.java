package es.uclm.sri.lastfm;

import de.umass.lastfm.Album;


/**
 * @author sernav
 * 
 * */
public interface IAnalisisLastfm {
    
    public void setNickUsuario(String nick);
    
    public String autenticarUsuario();
    
    public Album[] getTopAlbumsUsuario();
    
    public String getDatosUsuario();

}
