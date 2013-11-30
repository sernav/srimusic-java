package es.uclm.sri.lastfm;

import java.security.Key;

import de.umass.lastfm.User;


public class ClienteLastfm implements IDatosUsuarioLastfm {
    
    private static final String API_KEY = "58169c7f645f6ad54529f3012548fc3a";
    private static final String SECRET = "d99785f1e3ee9305b7c67b6e81bce5b1";
    private String userName = null;
    
    private User userLastfm;
    
    public ClienteLastfm(String userName) {
        this.userName = userName;
    }
    
    public ClienteLastfm(User user) {
        this.userLastfm = user;
        this.userName = user.getName();
    }

    @Override
    public String getNickUsuario() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMailUsuario() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key getKeyUsuario() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setKeyUsuario() {
        // TODO Auto-generated method stub
        
    }

}
