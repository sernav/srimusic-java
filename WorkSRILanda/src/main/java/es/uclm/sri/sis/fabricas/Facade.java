package main.java.es.uclm.sri.sis.fabricas;

import main.java.es.uclm.sri.lastfm.PlaybackDUsuario;
import main.java.es.uclm.sri.sis.entidades.Recomendacion;

/**
 * @author Sergio Navarro
 * 
 * Implementa el patrón facade para acceder a las fábricas desde fuera del sistema.
 * */
public class Facade {
    
    PlaybackDUsuario playback;
    FabricaDRecomendaciones fabrica;
    
    public Facade(String user) {
        playback = new PlaybackDUsuario(user);
        fabrica = new FabricaDRecomendaciones();
    }
    
    public Recomendacion[] generarRecomendacionesDUsuario() {
        playback.run();
        fabrica.setPlayblak(playback);
        fabrica.run();
        Recomendacion[] recomendaciones = fabrica.getRecomendaciones();
        
        return recomendaciones;
    }

}
