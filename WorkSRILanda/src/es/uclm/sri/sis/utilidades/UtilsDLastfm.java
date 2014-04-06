package es.uclm.sri.sis.utilidades;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Track;

public class UtilsDLastfm {
    
    private static String API_KEY = "58169c7f645f6ad54529f3012548fc3a";
    
    private UtilsDLastfm() {
        
    }
    
    public static Collection<String> extraerTagsLastfm(Album album) {
        HashMap<String, String> tags = new HashMap<String, String>();
        /*
         * El album tiene tags
         * */
        if (album.getTags().size() > 0) {
            return album.getTags();
        /*
         * El album no tiene tag y se buscan en los tracks del album
         * */
        } else {
            Collection<Track> tracks = album.getTracks();
            Iterator<Track> it = tracks.iterator();
            
            while (it.hasNext()) {
                Track track = it.next();
                String trackOrMbid = track.getMbid();
                if (trackOrMbid.length() == 0) {
                    trackOrMbid = track.getName();
                }
                
                Track trackAux = Track.getInfo(album.getArtist(), trackOrMbid, API_KEY);
                
                if (trackAux.getTags().size() > 0) {
                    Iterator<String> itTags = trackAux.getTags().iterator();
                    while (itTags.hasNext()) {
                        String tag = itTags.next();
                        tags.put(tag.trim(), tag);
                    }
                }
            }
        }
        /*
         * No hay tags en el album ni en los tracks del album.
         * Se recogen los tags del artista.
         * */
        if (tags.isEmpty() || tags.size() == 0) {
            Artist artista = Artist.getInfo(album.getArtist(), API_KEY);
            return artista.getTags();
        }
        
        return tags.values();
    }
    
    public static Collection<String> extraerTagsDAlbum(Album album) {
        return album.getTags();
    }
    
    public static Collection<String> extraerTagsDTracks(Album album) {
        HashMap<String, String> tags = new HashMap<String, String>();
        Collection<Track> tracks = album.getTracks();
        Iterator<Track> it = tracks.iterator();
        
        while (it.hasNext()) {
            Track track = it.next();
            String trackOrMbid = track.getMbid();
            if (trackOrMbid.length() == 0) {
                trackOrMbid = track.getName();
            }
            
            Track trackAux = Track.getInfo(album.getArtist(), trackOrMbid, API_KEY);
            
            if (trackAux.getTags().size() > 0) {
                Iterator<String> itTags = trackAux.getTags().iterator();
                while (itTags.hasNext()) {
                    String tag = itTags.next();
                    tags.put(tag.trim(), tag);
                }
            }
        }
        return tags.values();
    }
    
    public static Collection<String> extraerTagsDArtista(Album album) {
        Artist artista = Artist.getInfo(album.getArtist(), API_KEY);
        return artista.getTags();
    }
    
    public static String getApiKey() {
        return API_KEY;
    }

}
