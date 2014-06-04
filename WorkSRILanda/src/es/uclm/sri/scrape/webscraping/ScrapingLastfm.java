package es.uclm.sri.scrape.webscraping;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uclm.sri.scrape.AbstractWebScraping;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.Artista;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;
import es.uclm.sri.sis.utilidades.UtilsDAlbum;

/**
 * Producto concreto de la web Lastfm.
 * 
 * (Se utiliza la librería Jsoup)
 * 
 * @author Sergio Navarro
 * */
public class ScrapingLastfm extends AbstractWebScraping implements Serializable {
	
	private final static String SITE = "Last.fm";
	private final static String URL = "http://www.lastfm.es/music/+geo/spain";
	private final static String SubURL = "";
	
	private final static int NUM_PAGES = 30;

    public ScrapingLastfm(String rutaDestino) {
        super(SITE, URL, SubURL, NUM_PAGES, rutaDestino);
        scrappingWeb(URL, SubURL, NUM_PAGES, rutaDestino);
    }

    public void scrappingWeb(String url, String subUrl, int numPages, String destinyPath) {
        String numPage = "";
        String urlAnalyze = "";

        ArrayList<Artista> listaArtistas = new ArrayList<Artista>();
        
        Log.logScraping("Fábrica de Scraping. Producto Lastfm [lastfm.es]");
        
        Log.logScraping("Arrancando parseo web de " + url);
        Log.logScraping("Número de páginas: " + numPages);

        for (int iNumPage = 1; iNumPage <= numPages; iNumPage++) {
        	Log.logScraping("Extrayendo info. Página #" + iNumPage);
            numPage = String.valueOf(iNumPage);
            urlAnalyze = url + "?page=" + numPage;
            Document doc;

            try {
                doc = Jsoup.connect(urlAnalyze).timeout(10 * 1000).get();
                Elements links = doc.select("a[href]");

                for (Element link : links) {
                    String strLink = link.attr("abs:href");

                    if (strLink.contains("http://www.lastfm.es/music/")) {
                        if (!strLink.contains("http://www.lastfm.es/music/+tag") && !strLink.contains("http://www.lastfm.es/music/+hype")
                                && !strLink.contains("http://www.lastfm.es/music/+geo") && !strLink.contains("+events") && !strLink.contains("+free-music")) {
                            /*
                             *  Recogemos a cada uno de los artistas de la página
                             * */
                            Artista artista = new Artista();
                            Log.logScraping("Artista: " + link.text().trim());
                            artista.setNombre(link.text().trim());
                            Document docArtista = Jsoup.connect(link.attr("abs:href")).get();
                            procesarLinkAlbums(docArtista, artista);
                            listaArtistas.add(artista);
                        }

                    }

                    TratarCSVAlbum.generarCSVAlbums(listaAlbums, 4, destinyPath, null);
                }
            } catch (SocketTimeoutException sto) {
            	Log.logScraping(" >>> ERROR! Time out exception"  + sto.getMessage());
                sto.printStackTrace();
            } catch (IOException e) {
            	Log.logScraping(" >>> ERROR! IO Exception" + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
            	Log.logScraping(" >>> ERROR! General Exception " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void procesarLinkArtista(Document docArtista, Artista artista) {
        Elements linksArtista = docArtista.select("a[href]");

        for (Element link : linksArtista) {
            String strLink = link.attr("abs:href");
            Elements elements = link.getElementsByClass("g3 album-item-cover link-hook");
            if (strLink.contains("+albums")) {
                Document docAlbum;
                try {
                    docAlbum = Jsoup.connect(link.attr("abs:href")).get();
                    procesarLinkAlbums(docAlbum, artista);
                } catch (SocketTimeoutException sto) {
                	Log.logScraping(" >>> ERROR! Time out exception"  + sto.getMessage());
                    sto.printStackTrace();
                } catch (IOException e) {
                	Log.logScraping(" >>> ERROR! IO Exception" + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                	Log.logScraping(" >>> ERROR! General Exception " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void procesarLinkAlbums(Document docAlbum, Artista artista) {
        Elements lAlbums = docAlbum.select("a[class=g3 album-item-cover link-hook]");
        Elements lFechaAlbums = docAlbum.select("time[datetime]");
        Elements lTracksAlbums = docAlbum.select("span[itemprop=numTracks]");
        
        Log.logScraping("Procesando albums de " + artista.getNombre().toUpperCase());

        HashMap<String, Album> hashAlbums = new HashMap<String, Album>();
        int numEtiquetas = 0;

        for (int index = 0; index < lAlbums.size(); index++) {
            Element link = lAlbums.get(index);
            Element lFecha = null;
            String strFecha = "1 Enero 2000";
            try {
                lFecha = lFechaAlbums.get(index);
                strFecha = lFecha.text().trim();
            } catch (IndexOutOfBoundsException indexExp) {
            }
            Element lTracks = lTracksAlbums.get(index);
            String strLink = link.attr("abs:href");
            if (strLink.contains("/music/" + artista.getNombre().replace(' ', '+') + "/")
                    && !strLink.contains("/music/" + artista.getNombre().replace(' ', '+') + "/+")
                    && !strLink.contains("/music/" + artista.getNombre().replace(' ', '+') + "/_/") && !strLink.contains("oyentes")) {
                if (!link.text().trim().equals("")) {
                    if (!hashAlbums.containsKey(link.text().trim())) {
                    	/*
                    	 * Solo se procesan lo discos largos, LPs.
                    	 * */
                        if (Integer.parseInt(lTracks.text()) > 4) {
                            Album album = new Album();
                            album.setArtista(artista.getNombre());
                            album.setTitulo(UtilsDAlbum.tratarTituloAlbum(link.text()));
                            album.setFecha(strFecha);
                            Log.logScraping("Album #" + index + ": " + link.text().trim());
                            Log.logScraping(" > Número temas: " + lTracks.text());
                            Log.logScraping(" > Fecha publicación: " + strFecha);
                            try {
                                numEtiquetas = procesarEtiquetasAlbum(link, album);
                            } catch (Exception e) {
                                
                                e.printStackTrace();
                            }
                            if (numEtiquetas > 0)
                                listaAlbums.add(album);
                            hashAlbums.put(album.getTitulo().trim(), album);
                        }
                    }
                }
            }
        }
        artista.setAlbums(listaAlbums);
        artista.setNumAlbums(listaAlbums.size());
    }

    private int procesarEtiquetasAlbum(Element element, Album album) {
        ArrayList<String> listaEtiquetas = new ArrayList<String>();
        try {
            Document docAlbum = Jsoup.connect(element.attr("abs:href")).get();
            Elements links = docAlbum.select("a[href]");
            for (Element link : links) {
                String strLink = link.attr("abs:href") + link.text().trim();
                if (strLink.contains("/tag/")) {
                    if (!link.text().equals("")) {
                        Log.logScraping(" >> Tag: " + link.text().trim());
                        if (!link.text().trim().equals("") && !link.text().trim().equals("albums i own") && !Character.isDigit(link.text().charAt(0)))
                            listaEtiquetas.add(link.text().trim());
                    }
                }
            }
            album.setEtiquetas(listaEtiquetas);
        } catch (SocketTimeoutException sto) {
        	Log.logScraping(" >>> ERROR! Time out exception "  + sto.getMessage());
            sto.printStackTrace();
        } catch (IOException e) {
        	Log.logScraping(" >>> ERROR! IO Exception "  + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
        	Log.logScraping(" >>> ERROR! General Exception "  + e.getMessage());
            e.printStackTrace();
        }

        return listaEtiquetas.size();
    }

    public void generarCSV() {
        // TODO Auto-generated method stub

    }

}
