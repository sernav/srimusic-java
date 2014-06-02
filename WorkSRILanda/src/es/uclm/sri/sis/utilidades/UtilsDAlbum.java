package es.uclm.sri.sis.utilidades;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.CallException;
import de.umass.lastfm.Track;
import es.uclm.sri.sis.log.Log;

/**
 * Utilidades específicas para tratamiendo de la entidad Album de la aplicación
 * y de Lastfm.
 * 
 * @author Sergio Navarro
 * */
public class UtilsDAlbum {
	
	private static String API_KEY_LASTFM = "58169c7f645f6ad54529f3012548fc3a";
    
    private UtilsDAlbum() {
    }
    
    public static String tratarTituloAlbum(String titulo) {
        boolean continuar = true;
        String newTitulo = "";
        titulo = titulo.trim();
        for (int i=0; i < titulo.length() && continuar; i++) {
            if (Character.isDigit(titulo.charAt(i)))
                continuar = false;
            else
                newTitulo += titulo.charAt(i);
        }
        return newTitulo;
    }
    
    /**
     * Las etiquetas del disco pueden ser compuestas o ser complejas
     * (indietronica, electropop, hip-hop o hip hop, singer-songwriter, female vocalist,
     * male vocalist, drum and bass y trip hop)
     * 
     * @param etiqueta: String
     * @return boolean
     * */
    public static boolean isEtiquetaCompuesta(String etiqueta) {
        if(etiqueta.equals("indietronica") ||
                etiqueta.equals("electropop")) {
            return true;
        }
        
        if (etiqueta.contains("-") && !etiqueta.equals("hip-hop")
                && !etiqueta.equals("singer-songwriter")) {
            return true;
        }
        
        if (etiqueta.contains(" ") && !etiqueta.equals("hip hop")
                && !etiqueta.equals("trip hop") && !etiqueta.equals("trip hop")
                && !etiqueta.equals("female vocalist") && !etiqueta.equals("male vocalist") 
                && !etiqueta.equals("drum and bass")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Decompone etiqueta complejas en una o varias etiquetas reconocibles por
     * el sistema.
     * 
     * @param etiqueta: String
     * @return etiquetas del sistema: String[]
     * */
    public static String[] descomponerEtiquetaCompuesta(String etiqueta) {
        String[] etiquetas = new String[2];
        if(etiqueta.equals("indietronica")) {
            etiquetas[0] = "indie";
            etiquetas[1] = "electronic";
        } else if (etiqueta.equals("electropop")){
            etiquetas[0] = "electronic";
            etiquetas[1] = "pop";
        } else if (etiqueta.contains("-") && 
                !etiqueta.equals("hip-hop") && !etiqueta.equals("singer-songwriter")) {
            //if (etiqueta.split("-").length == 2)
                etiquetas = etiqueta.split("-");
        } else if (etiqueta.contains(" ") && !etiqueta.equals("hip hop")
                && !etiqueta.equals("trip hop") && !etiqueta.equals("trip hop")
                && !etiqueta.equals("female vocalist") && !etiqueta.equals("male vocalist") 
                && !etiqueta.equals("drum and bass")) {
            etiquetas = etiqueta.split(" ");
        }
        return etiquetas;
    }
    
    /**
     * Función para obtener los tag de un album de Lastfm.
     * Captura las excepciones del uso de Lastfm.
     * 
     * @param album de Lastfm
     * @return colección de tags: Collection
     * */
    public static Collection<String> extraerTagsLastfm(Album album) {
        HashMap<String, String> tags = new HashMap<String, String>();
        try {
            /*
             * El album tiene tags
             */
            if (album.getTags().size() > 0) {
                return album.getTags();
                /*
                 * El album no tiene tag y se buscan en los tracks del album
                 */
            } else {
                Collection<Track> tracks = album.getTracks();
                Iterator<Track> it = tracks.iterator();

                while (it.hasNext()) {
                    Track track = it.next();
                    String trackOrMbid = track.getMbid();
                    if (trackOrMbid.length() == 0) {
                        trackOrMbid = track.getName();
                    }

                    Track trackAux = Track.getInfo(album.getArtist(), trackOrMbid, API_KEY_LASTFM);

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
             * No hay tags en el album ni en los tracks del album. Se recogen
             * los tags del artista.
             */
            if (tags.isEmpty() || tags.size() == 0) {
                Artist artista = Artist.getInfo(album.getArtist(), API_KEY_LASTFM);
                return artista.getTags();
            }
        } catch (CallException e) {
            e.printStackTrace();
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción al invocar servicio LastFm! " + e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción General! " + e.getMessage());
        }

        return tags.values();
    }

    public static Collection<String> extraerTagsDAlbum(Album album) {
        return album.getTags();
    }
    
    /**
     * Obtiene la lista de etiquetas para los track de un album de Lastfm
     * Captura las excepciones del uso de Lastfm.
     * 
     * @param album de Lastfm
     * @return colección de tags: Collection
     * */
    public static Collection<String> extraerTagsDTracks(Album album) {
        HashMap<String, String> tags = new HashMap<String, String>();
        Collection<Track> tracks = album.getTracks();
        Iterator<Track> it = tracks.iterator();
        try {
            while (it.hasNext()) {
                Track track = it.next();
                String trackOrMbid = track.getMbid();
                if (trackOrMbid.length() == 0) {
                    trackOrMbid = track.getName();
                }

                Track trackAux = Track.getInfo(album.getArtist(), trackOrMbid, API_KEY_LASTFM);

                if (trackAux.getTags().size() > 0) {
                    Iterator<String> itTags = trackAux.getTags().iterator();
                    while (itTags.hasNext()) {
                        String tag = itTags.next();
                        tags.put(tag.trim(), tag);
                    }
                }
            }
        } catch (CallException e) {
            e.printStackTrace();
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción al invocar servicio LastFm! " + e.getMessage());
        } catch (Exception e) {
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción General! " + e.getMessage());
        }
        return tags.values();
    }
    
    /**
     * Obtiene la lista de etiquetas del artista de un album de Lastfm
     * Captura las excepciones del uso de Lastfm.
     * 
     * @param album de Lastfm
     * @return colección de tags: Collection
     * */
    public static Collection<String> extraerTagsDArtista(Album album) {
        Artist artista = null;
        try {
            artista = Artist.getInfo(album.getArtist(), API_KEY_LASTFM);
        } catch (CallException e) {
            e.printStackTrace();
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción al invocar servicio LastFm! " + e.getMessage());
        } catch (Exception e) {
            Log.log(e, "(" + UtilsDAlbum.class.getSimpleName() + ") Excepción General! " + e.getMessage());
        }
        if (artista != null) {
            return artista.getTags();
        } else {
            return null;
        }

    }

    public static String getApiKeyLastfm() {
        return API_KEY_LASTFM;
    }

}
