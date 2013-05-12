package es.uclm.sri.htmlparser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import es.uclm.sri.csv.TratarCSVAlbum;
import es.uclm.sri.sis.entidades.Album;

public class HtmlParserMondosonoro {

	private final static String URL_MONDOSONORO = "http://www.mondosonoro.com/Cr%C3%ADticas/Discos.aspx";
	private final static String SUBURL_MONDOSONORO = "http://www.mondosonoro.com/Critica-Discos/";

	private final static String ID_ELEMENT_TITULO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_titularLabel";
	private final static String ID_ELEMENT_ARTISTA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_grupoLabel";
	private final static String ID_ELEMENT_GENERO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_generoLabel";
	private final static String ID_ELEMENT_PAIS = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_paisEdicionLabel";
	private final static String ID_ELEMENT_FECHA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_horaFechaPublicacionLabel";

	private final static int NUMPAGES = 140;

	private final static String DESTINY_PATH = "/Users/sergionavarro/PFC/CSV_Albums_Mondosonoro";

	private static final Logger logger = Logger
			.getLogger(HtmlParserAlbum.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] elements = { ID_ELEMENT_TITULO, ID_ELEMENT_ARTISTA,
				ID_ELEMENT_GENERO, ID_ELEMENT_PAIS, ID_ELEMENT_FECHA };
		scrappingMondosonoro(URL_MONDOSONORO, SUBURL_MONDOSONORO, NUMPAGES,
				elements, DESTINY_PATH);
	}

	protected static void scrappingMondosonoro(String url, String subUrl,
			int numPages, String[] idElements, String destinyPath) {
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
						Document docAlbum = Jsoup
								.connect(link.attr("abs:href")).get();
						Album album = new Album();
						if (!link.text().trim().equals("")) {
							album = procesarElementosAlbum(docAlbum, album,
									idElements);
							listaAlbums.add(album);
						}
						// }
					}
				}
				TratarCSVAlbum.generarCSVAlbums(listaAlbums, 1, destinyPath,
						logger);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String procesarElementoHtml(Element linkAlbum,
			String idElement) throws IOException {
		Document doc = Jsoup.connect(linkAlbum.attr("abs:href")).get();
		Element element = doc.getElementById(idElement);
		Node node = element.childNode(0);

		return node.attr("text");
	}

	private static Album procesarElementosAlbum(Document docAlbum, Album album,
			String[] elementos) {
		for (int index = 0; index < elementos.length; index++) {
			try {
				Element element = docAlbum.getElementById(elementos[index]);
				if (element != null) {
					Node node = element.childNode(0);
					String strElement = node.attr("text");
					switch (index) {
					case 0:
						album.setTitulo(strElement);
						break;
					case 1:
						album.setArtista(strElement);
						break;
					case 2:
						ArrayList<String> genero = new ArrayList<String>();
						genero.add(strElement);
						album.setEtiquetas(genero);
						break;
					case 3:
						album.setPais(strElement);
						break;
					case 4:
						album.setFecha(strElement);
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return album;
	}

}
