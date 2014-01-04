package es.uclm.sri.sis.fabricas;

import java.util.HashMap;

import de.umass.lastfm.User;
import es.uclm.sri.persistencia.admon.AdmonUsuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;
import es.uclm.sri.sis.utilidades.UtilsDLastfm;

public class FabricaDUsuarios implements IFabrica {
    
    private String usuario;
    private User usuarioLfm;
    private HashMap<Integer, String> avisosDSistema;
    
    public FabricaDUsuarios(String usuario, boolean isUserLfm) {
        this.usuario = usuario;
        if (isUserLfm)
            this.usuarioLfm = User.getInfo(usuario, UtilsDLastfm.getApiKey());
        
        this.avisosDSistema = new HashMap<Integer, String>();
        
        if (usuario == null || usuario.equals("")) {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                    "No hay usuario");
        }
    }
    
    public void run() {
        /**
         * 1. Comprobar si el usuario est‡ en el sistema
         * 2. Si no est‡ lo insertamos
         * */
        AdmonUsuarios admonUsuario = new AdmonUsuarios();
        Dusuarios uapp = null;
        if (this.usuarioLfm != null) {
            uapp = admonUsuario.devolverUsuario(this.usuarioLfm);
        } else if (this.usuario != null && this.usuario.length() > 0) {
            uapp = admonUsuario.devolverUsuario(this.usuario);
        } else {
            this.avisosDSistema.put(new Integer(avisosDSistema.size() + 1), 
                    "No se puede buscar un usuario sin usuario ÁAs’ no!");
        }
        
        if (uapp == null && this.usuarioLfm != null) {
            /**
             * 1. Se inserta el usuario
             * 2. Se generan sus pesos iniciales
             * */
            admonUsuario.insertarUsuario(this.usuarioLfm);
        } else {
            /**
             * Se actualizan los pesos del usuario
             * */
        }
        
    }

    public String getUsuario() {
        return usuario;
    }

    public User getUsuarioLfm() {
        return usuarioLfm;
    }

    @Override
    public void aplicarSistemaDReglas() {
        // TODO Auto-generated method stub
        
    }

    public HashMap<Integer, String> getAvisosDSistema() {
        return this.avisosDSistema;
    }

}
