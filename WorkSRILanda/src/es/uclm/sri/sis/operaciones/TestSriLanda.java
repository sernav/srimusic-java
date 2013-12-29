package es.uclm.sri.sis.operaciones;

import de.umass.lastfm.Album;
import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.sis.ponderacion.PonderacionDAlbum;

public class TestSriLanda {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PlaybackDUsuario playback = new PlaybackDUsuario("djAguadilla");
        playback.run();
        
        FabricaDRecomendaciones fabrica = new FabricaDRecomendaciones(playback);
        fabrica.procesarDatos();
        
        PonderacionDAlbum pondera = null;
        
        for (int i = 0; i < playback.getTopAlbumsUsuario().length; i++) {
            Album album = playback.getTopAlbumsUsuario()[i];
            
            pondera = new PonderacionDAlbum(album);
            pondera.procesar();
            
            System.out.println("Album: " + album.getName() + " " + album.getArtist());
            
        }

    }

}
