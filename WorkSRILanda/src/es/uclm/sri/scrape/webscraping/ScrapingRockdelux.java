package es.uclm.sri.scrape.webscraping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uclm.sri.scrape.AbstractWebScraping;
import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;

/**
 * Producto concreto de la web Rockdelux.
 * 
 * (Se utiliza la librería Jsoup)
 * 
 * @author Sergio Navarro
 * */
public class ScrapingRockdelux extends AbstractWebScraping {
	
	private final static String SITE = "Rockdelux";
	private final static String URL = "http://www.rockdelux.com/discos/albumes/page";
	private final static String SubURL = "http://www.rockdelux.com/discos/p/";
	
	private final static int NUM_PAGES = 12;

    public ScrapingRockdelux(String rutaDestino) {
        super(SITE, URL, SubURL, NUM_PAGES, rutaDestino);
        logger = Logger.getLogger(ScrapingRockdelux.class);
    }

    public void scrappingWeb(String url, String subUrl, int numPages, String destinyPath) {
        String[] tipoText = { "Artista", "Disco" };
        int index = 0;
        int numMaxEtiquetas = 0;
        String numPage = "";
        String urlAnalyze = "";
        Album album = null;
        ArrayList<Album> listaAlbums = new ArrayList<Album>();
        
        Log.logScraping("Fábrica de Scraping. Producto Rockdelux [www.rockdelux.com].");

        Log.logScraping("Arrancando parseo web de " + url);
        Log.logScraping("Número de páginas: " + numPages);

        for (int iNumPage = 1; iNumPage <= numPages; iNumPage++) {
            numPage = String.valueOf(iNumPage);
            urlAnalyze = url + numPage + ".html";
            Log.logScraping("Fetching..." + urlAnalyze);

            Document doc;
            try {
                doc = Jsoup.connect(urlAnalyze).get();
                Elements links = doc.select("a[href]");

                Log.logScraping("\nLinks en página: " + links.size());
                for (Element link : links) {
                    if (index == 0) {
                        album = new Album();
                        Log.logScraping("Creando nuevo Album");
                    }

                    String strLink = link.attr("abs:href") + link.text().trim();

                    if (strLink.contains(subUrl)) {
                        if (!link.text().equals("")) {
                            System.out.println(link.text().trim());
                            logger.info(tipoText[index] + ": " + link.text().trim());

                            if (index > 0) {
                                album.setTitulo(link.text().trim());
                                Log.logScraping("Título album: " + album.getTitulo());
                                ArrayList<String> listEtiquetas = procesarLinkAlbum(link);
                                album.setEtiquetas(listEtiquetas);
                                Iterator it = listEtiquetas.iterator();
                                Log.logScraping("Etiquetas album");
                                while (it.hasNext()) {
                                    String etq = (String) it.next();
                                    Log.logScraping(etq);
                                }
                                try {
                                    album.setFecha(listEtiquetas.get(1).substring(0, 4));
                                    Log.logScraping("Fecha publicación: " + album.getFecha());
                                } catch (Exception excp) {
                                	Log.logScraping("ERROR! Fecha de album incorrecta");
                                    try {
                                        album.setFecha(listEtiquetas.get(0));
                                    } catch (Exception excp2) {
                                    	Log.logScraping("ERROR! Doble error de fecha");
                                        album.setFecha("01/01/1900");
                                    }
                                }
                                listaAlbums.add(album);
                                index--;

                                if (numMaxEtiquetas < listEtiquetas.size())
                                    numMaxEtiquetas = listEtiquetas.size();

                            } else {
                                album.setArtista(link.text().trim());
                                Log.logScraping("Artista: " + album.getArtista());
                                index++;
                            }
                        }
                    }
                }
                /*
                 * Crear el CSV con los datos
                 */
                TratarCSVAlbum.generarCSVAlbums(listaAlbums, numMaxEtiquetas, destinyPath, logger);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.logScraping("Nº de discos: " + listaAlbums.size());
        }

    }

    private static ArrayList<String> procesarLinkAlbum(Element linkAlbum) throws IOException {
    	Log.logScraping("Procesando etiquetas...");
        ArrayList<String> listaEtiquetas = new ArrayList<String>();
        Document docAlbum = Jsoup.connect(linkAlbum.attr("abs:href")).get();
        Elements links = docAlbum.select("a[href]");
        for (Element link : links) {
            String strLink = link.attr("abs:href") + link.text().trim();
            if (strLink.contains("http://www.rockdelux.com/etiquetas/")) {
                if (!link.text().equals("")) {
                    listaEtiquetas.add(link.text());
                }
            }
        }
        return listaEtiquetas;
    }

    @Override
    public void generarCSV() {
        // TODO Auto-generated method stub

    }
}
