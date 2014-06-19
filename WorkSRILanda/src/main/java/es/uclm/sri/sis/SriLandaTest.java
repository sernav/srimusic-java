package main.java.es.uclm.sri.sis;

import main.java.es.uclm.sri.lastfm.PlaybackDUsuario;
import main.java.es.uclm.sri.sis.fabricas.FabricaDRecomendaciones;

public class SriLandaTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        PlaybackDUsuario playback = new PlaybackDUsuario("djAguadilla");
        playback.run();
        
        FabricaDRecomendaciones mainFactory = new FabricaDRecomendaciones(playback);
        mainFactory.run();
        
    }

}
