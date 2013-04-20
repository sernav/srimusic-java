package es.uclm.sri.htmlparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uclm.sri.csv.UtilCSVAlbum;
import es.uclm.sri.objnegocio.Album;

public class HtmlParserAlbum {
	
	private final static String URL_ROCKDELUXE = "http://www.rockdelux.com/discos/albumes/page";
	private final static String SUBURL_ROCKDELUXE = "http://www.rockdelux.com/discos/p/";
	private final static int NUMPAGES = 12;
	
	private final static String DESTINY_PATH = "";
	
	private static final Logger logger = Logger.getLogger(HtmlParserAlbum.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		scrapingRockdelux(URL_ROCKDELUXE, SUBURL_ROCKDELUXE, NUMPAGES, DESTINY_PATH);
		
	}
	
	protected static void scrapingRockdelux(String url, String subUrl, int numPages, String destinyPath) {
		String[] tipoText = { "Artista", "Disco" };
		int index = 0;
		int numMaxEtiquetas = 0;
		String numPage = "";
		String urlAnalyze = "";
		Album album = null;
		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		
		logger.info("Arrancando parseo web de " + url);
		logger.info("N�mero de p�ginas: " + numPages);
		
		for (int iNumPage = 1; iNumPage <= numPages; iNumPage++) {
			numPage = String.valueOf(iNumPage);
			urlAnalyze = url + numPage + ".html";
			logger.info("Fetching..." + urlAnalyze);
	
			Document doc;
			try {
				doc = Jsoup.connect(urlAnalyze).get();
				Elements links = doc.select("a[href]");
	
				logger.info("\nLinks en p�gina: " + links.size());
				for (Element link : links) {
					if (index == 0) {
						album = new Album();
						logger.info("Creando nuevo Album");
					}
					
					String strLink = link.attr("abs:href") + link.text().trim();
					
					if (strLink.contains(subUrl)) {
						if (!link.text().equals("")) {
							logger.info(tipoText[index] + ": " + link.text().trim());
	
							if (index > 0) {
								album.setTitulo(link.text().trim());
								logger.info("T�tulo album: " + album.getTitulo());
								ArrayList<String> listEtiquetas = procesarLinkAlbum(link);
								album.setEtiquetas(listEtiquetas);
								Iterator it = listEtiquetas.iterator();
								logger.info("Etiquetas album");
								while (it.hasNext()) {
									String etq = (String) it.next();
									logger.info(etq);
								}
								try {
									album.setAnyo(Integer.parseInt(listEtiquetas.get(1).substring(0, 4)));
									logger.info("Anyo album: " + album.getAnyo());
								} catch (Exception excp) {
									logger.error("Fecha de album incorrecta");
									try {
										album.setAnyo(Integer.parseInt(listEtiquetas.get(0)));
									} catch (Exception excp2) {
										logger.error("Doble error de fecha");
										album.setAnyo(1900);
									}
								}
								listaAlbums.add(album);
								index--;
	
								if (numMaxEtiquetas < listEtiquetas.size())
									numMaxEtiquetas = listEtiquetas.size();
	
							} else {
								album.setArtista(link.text().trim());
								logger.info("Artista: " + album.getArtista());
								index++;
							}
						}
					}
				} 
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("N� de discos: " + listaAlbums.size());
		}
		/*
		 * Crear el CSV con los datos
		 * */
		UtilCSVAlbum.generarCSVAlbums(listaAlbums, numMaxEtiquetas, destinyPath, logger);
		
	}

	private static ArrayList<String> procesarLinkAlbum(Element linkAlbum)
			throws IOException {
		logger.info("Procesando etiquetas...");
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
}
