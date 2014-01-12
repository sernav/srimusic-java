package es.uclm.sri.sis.operaciones;

import com.zeloon.deezer.client.DeezerClient;
import com.zeloon.deezer.domain.User;
import com.zeloon.deezer.domain.internal.UserId;
import com.zeloon.deezer.io.HttpResourceConnection;

import de.umass.lastfm.Album;
import deezer.j2me.DZMidlet;
import es.uclm.sri.lastfm.PlaybackDUsuario;
import es.uclm.sri.sis.fabricas.FabricaDRecomendaciones;

public class TestSriLanda {

    /**
     * @param args
     */
    public static void main(String[] args) {
        PlaybackDUsuario playback = new PlaybackDUsuario("djAguadilla");
        playback.run();
        
        FabricaDRecomendaciones fabrica = new FabricaDRecomendaciones(playback);
        fabrica.run();
        
        DZMidlet dz = deezer.j2me.DZMidlet.instance;
        
        final DeezerClient deezerClient = new DeezerClient(new HttpResourceConnection());
        UserId idUser = new UserId((long) 99393393);
        User udz = deezerClient.get(idUser);

        
        PonderacionDAlbum pondera = null;
        
        for (int i = 0; i < playback.getTopAlbumsUsuario().length; i++) {
            Album album = playback.getTopAlbumsUsuario()[i];
            
            pondera = new PonderacionDAlbum(album);
            pondera.procesar();
            
            System.out.println("Album: " + album.getName() + " " + album.getArtist());
            
        }

    }

}
