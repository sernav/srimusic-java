package logica;

import es.uclm.sri.sis.entidades.Recomendacion;
import es.uclm.sri.sis.fabricas.Facade;

public class RecomendacionesByLastfm {
    
    private static Facade facade;

    public static void generarRecomendacionesByLastfm(String usuario) {
        facade = new Facade(usuario);
        Recomendacion[] recomendaciones = facade.generarRecomendacionesDUsuario();
    }

}
