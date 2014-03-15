package es.uclm.sri.sis;

import java.io.File;

import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.sis.fabricas.FabricaDRecomendaciones;

public class SriLandaTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        PlaybackDUsuario playback = new PlaybackDUsuario("djAguadilla");
        playback.run();
        
        File file = new File("es/uclm/sri/recursos/xml/sqlMapConfig.xml");
        
        FabricaDRecomendaciones mainFactory = new FabricaDRecomendaciones(playback);
        mainFactory.run();
        
    }

}
