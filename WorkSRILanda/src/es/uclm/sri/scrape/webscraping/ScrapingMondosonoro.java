package es.uclm.sri.scrape.webscraping;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;
import es.uclm.sri.scrape.AbstractWebScraping;

/**
 * Producto Mondosonoro.
 * Se utiliza la librer’a Jsoup.
 * 
 * @author Sergio Navarro
 * */
public class ScrapingMondosonoro extends AbstractWebScraping {

	private final String ID_ELEMENT_TITULO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_titularLabel";
	private final String ID_ELEMENT_ARTISTA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_grupoLabel";
	private final String ID_ELEMENT_GENERO = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_generoLabel";
	private final String ID_ELEMENT_PAIS = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_paisEdicionLabel";
	private final String ID_ELEMENT_FECHA = "dnn_ctr587_ViewDetalleCriticaObra_detalleCriticaObraFormView_horaFechaPublicacionLabel";
	
	public ScrapingMondosonoro(String rutaDestino) {
	    super("Mondosonoro", "http://www.mondosonoro.com/Cr%C3%ADticas/Discos.aspx\"",
                "http://www.mondosonoro.com/Critica-Discos/", 140, rutaDestino);
        logger = Logger.getLogger(ScrapingMondosonoro.class);
	}

	public void scrappingWeb(String url, String subUrl, int numPages,
			String destinyPath) {
		Log.logScraping("F‡brica de Scraping. Producto Mondosonoro [www.mondosonoro.com].");
		
		Log.logScraping("Arrancando parseo web de " + url);
        Log.logScraping("Nœmero de p‡ginas: " + numPages);
		
		String[] elements = { ID_ELEMENT_TITULO, ID_ELEMENT_ARTISTA,
				ID_ELEMENT_GENERO, ID_ELEMENT_PAIS, ID_ELEMENT_FECHA };
		scrappingMondosonoro(this.url, this.subUrl, this.numPaginas,
				elements, this.rutaDestino);
		
	}

    protected void scrappingMondosonoro(String url, String subUrl,
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
							Log.logScraping("Album: " + album.getTitulo());
							Log.logScraping(" > Artista: " + album.getArtista());
                            Log.logScraping(" > Nœmero temas: " + album.getNumTemas());
                            Log.logScraping(" > Fecha publicaci—n: " + album.getFecha());
                            Log.logScraping(" > Tags: " + album.getEtiquetas().toString());
							listaAlbums.add(album);
						}
					}
				}
				TratarCSVAlbum.generarCSVAlbums(listaAlbums, 1, destinyPath,
						logger);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Album procesarElementosAlbum(Document docAlbum, Album album,
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

    @Override
    public void generarCSV() {
        // TODO Auto-generated method stub
        
    }

}
