package es.uclm.sri.jsrilanda.logica;

import main.java.es.uclm.sri.sis.entidades.Recomendacion;
import main.java.es.uclm.sri.sis.fabricas.Facade;

public class RecomendacionesByLastfm {
    
    private static Facade facade;

    public static Recomendacion[] generarRecomendacionesByLastfm(String usuario) {
        facade = new Facade(usuario);
        Recomendacion[] recomendaciones = facade.generarRecomendacionesDUsuario();
        
        return recomendaciones;
    }

}
