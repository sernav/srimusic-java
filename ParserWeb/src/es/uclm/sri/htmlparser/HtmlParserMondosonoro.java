package es.uclm.sri.htmlparser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import es.uclm.sri.objnegocio.Album;

public class HtmlParserMondosonoro {
	
	private final static String URL_MONDOSONORO = "http://www.mondosonoro.com/Cr%C3%ADticas/Discos.aspx";
	private final static String PAG_URL_MONDOSONORO = "?p=0&o=2&e=1";
	private final static String SUBURL_MONDOSONORO = "http://www.mondosonoro.com/Critica-Discos/";
	
	private final static String ID_ELEMENT_TITULO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_titularLabel";
	private final static String ID_ELEMENT_ARTISTA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_grupoLabel";
	private final static String ID_ELEMENT_GENERO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_generoLabel";
	private final static String ID_ELEMENT_PAIS = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_paisEdicionLabel";
	private final static String ID_ELEMENT_FECHA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_horaFechaPublicacionLabel";
	
	private final static int NUMPAGES = 0;
	
	private final static String DESTINY_PATH = "/Users/sergionavarro/PFC/CSV_Albums_Mondosonoro";
	
	private static final Logger logger = Logger.getLogger(HtmlParserAlbum.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document doc;
		try {
			doc = Jsoup.connect(URL_MONDOSONORO).get();
			Elements links = doc.select("a[href]");
	
			logger.info("\nLinks en p‡gina: " + links.size());
			for (Element link : links) {
				
				String strLink = link.attr("abs:href") + link.text().trim();
				System.out.println(strLink);
				
				if (strLink.contains(SUBURL_MONDOSONORO)) {
					Album album = new Album();
					album.setTitulo(link.text().trim());
				}
			}
			System.out.println("FIN");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void scrappingMondosonoro(String url, String subUrl, int numPages, String[] idElements, String destinyPath) {
		String numPage = "";
		String urlAnalyze = "";
		
		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		
		for (int iNumPage = 1; iNumPage <= numPages; iNumPage++) {
			numPage = String.valueOf(iNumPage);
			urlAnalyze = url + "?p=" + numPage + "&o=2&e=1";
			Document doc;
			
			try {
				doc = Jsoup.connect(urlAnalyze).get();
				Elements links = doc.select("a[href]");
				
				for (Element link : links) {
					
					String strLink = link.attr("abs:href") + link.text().trim();
					System.out.println(strLink);
					
					if (strLink.contains(subUrl)) {
						Document docAlbum = Jsoup.connect(link.attr("abs:href")).get();
						for (int index = 0; index < idElements.length; index++) {
							Album album = new Album();
							album.setTitulo(link.text().trim());
							String vElement = procesarElementoHtml(link, idElements[index]);
							switch (index) {
								case 0:
									album.setTitulo(vElement);
									break;
								case 1:
									album.setArtista(vElement);
									break;
								case 2:
									ArrayList<String> genero = new ArrayList<String>();
									genero.add(vElement);
									album.setEtiquetas(genero);
									break;
								case 3:
									album.setAnyo(Integer.parseInt(vElement));
									break;
								default:
									break;
							}
							listaAlbums.add(album);
						}
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	private static String procesarElementoHtml(Element linkAlbum, String idElement) throws IOException {
		Document doc = Jsoup.connect(linkAlbum.attr("abs:href")).get();
		Element element = doc.getElementById(idElement);
		Node node = element.childNode(0);
		
		return node.attr("text");
	}

}
