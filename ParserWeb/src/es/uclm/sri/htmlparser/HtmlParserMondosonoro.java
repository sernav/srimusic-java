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
	
	private final static String URL_MONDOSONORO = "http://www.mondosonoro.com/Cr%C3%ADticas/Discos.aspx?p=0&o=2&e=1";
	private final static String PAG_URL_MONDOSONORO = "?p=0&o=2&e=1";
	private final static String SUBURL_MONDOSONORO = "http://www.mondosonoro.com/Critica-Discos/";
	private final static int NUMPAGES = 0;
	
	private final static String DESTINY_PATH = "/Users/sergionavarro/PFC/CSV_Albums";
	
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
					procesarLinkAlbum(link);
				}
			}
			System.out.println("FIN");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> procesarLinkAlbum(Element linkAlbum)
			throws IOException {
		logger.info("Procesando etiquetas...");
		ArrayList<String> listaEtiquetas = new ArrayList<String>();
		Document docAlbum = Jsoup.connect(linkAlbum.attr("abs:href")).get();
		
		//Genero
		Element elemGenero = docAlbum.getElementById("dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_generoLabel");
		//Pa’s edici—n
		Element elemPaisEdicion = docAlbum.getElementById("dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_paisEdicionLabel");
		//Fecha publicacion
		Element elemFechPubli = docAlbum.getElementById("dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_horaFechaPublicacionLabel");
		
		
		Node nodeGenero = elemGenero.childNode(0);
		String textGenero = nodeGenero.attr("text");
		
		Node nodePaisEdi = elemPaisEdicion.childNode(0);
		String textPaisEdi = nodePaisEdi.attr("text");
		
		Node nodeFechPubli = elemFechPubli.childNode(0);
		String textFechPubli = nodeFechPubli.attr("text");
		
		return listaEtiquetas;
	}

}
