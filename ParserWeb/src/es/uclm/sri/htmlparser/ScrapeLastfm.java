package es.uclm.sri.htmlparser;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.uclm.sri.objnegocio.Album;

public class ScrapeLastfm {
	
	public static final String URL_LASTFM = "http://www.lastfm.es/music";
	
	public static final String DESTINY_PATH = "/Users/sergionavarro/PFC/CSV_Albums_Lastfm.csv";
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		scrappingLastfm(URL_LASTFM, "", 1, DESTINY_PATH);
	}
	
	protected static void scrappingLastfm(String url, String subUrl, int numPages, String destinyPath) {
		String numPage = "";
		String urlAnalyze = "";

		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		ArrayList<Document> listaArtistas = new ArrayList<Document>();

		for (int iNumPage = 1; iNumPage <= numPages; iNumPage++) {
			numPage = String.valueOf(iNumPage);
			urlAnalyze = url + "?page=" + numPage;
			Document doc;

			try {
				doc = Jsoup.connect(urlAnalyze).get();
				Elements links = doc.select("a[href]");
				
				for (Element link : links) {
					String strLink = link.attr("abs:href");

					if (strLink.contains("http://www.lastfm.es/music/")) {
						if (!strLink.contains("http://www.lastfm.es/music/+tag") &&
								!strLink.contains("http://www.lastfm.es/music/+hype") &&
								!strLink.contains("http://www.lastfm.es/music/+geo")) {
						//Recogemos a cada uno de los artistas de la p‡gina
							System.out.println(link.text().trim());
							Document docArtista = Jsoup
									.connect(link.attr("abs:href")).get();
						}
						
					}
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	private static void procesarLinkArtista(Document docArtista) {
		Elements linksArtista = docArtista.select("a[href]");
		
		for (Element link : linksArtista) {
			String strLink = link.attr("abs:href") + link.text().trim();
			if (strLink.contains("")) {
				
			}
			System.out.println(strLink);
			
		}
	}


}
