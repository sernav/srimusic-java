package es.uclm.sri.htlmparser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVPrinter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.csvreader.CsvWriter;

import es.uclm.sri.objnegocio.Album;

public class HtmlParserAlbum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String numPage = "";
		String[] tipoText = { "Artista", "Disco" };
		int index = 0;
		int numMaxEtiquetas = 0;
		Album album = null;
		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		for (int iNumPage = 1; iNumPage <= 3; iNumPage++) {
			numPage = String.valueOf(iNumPage);
			String url = "http://www.rockdelux.com/discos/albumes/page" + numPage
					+ ".html";
			System.out.println("Fetching..." + url);
	
			Document doc;
			try {
				doc = Jsoup.connect(url).get();
				Elements links = doc.select("a[href]");
	
				System.out.println("\nLinks: " + links.size());
				for (Element link : links) {
					if (index == 0)
						album = new Album();
					
					String strLink = link.attr("abs:href") + link.text().trim();
					
					if (strLink.contains("http://www.rockdelux.com/discos/p/")) {
						if (!link.text().equals("")) {
							System.out.println(tipoText[index] + ": "
									+ link.text().trim());
	
							if (index > 0) {
								album.setTitulo(link.text().trim());
								ArrayList<String> listEtiquetas = procesarLinkAlbum(link);
								album.setEtiquetas(listEtiquetas);
								album.setAnyo(1900);
								listaAlbums.add(album);
								index--;
	
								if (numMaxEtiquetas < listEtiquetas.size())
									numMaxEtiquetas = listEtiquetas.size();
	
							} else {
								album.setArtista(link.text().trim());
								index++;
							}
						}
					}
				} 
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("N¼ de discos: " + listaAlbums.size());
		}

		generarCSVAlbums(listaAlbums, numMaxEtiquetas);
		
	}

	private static ArrayList<String> procesarLinkAlbum(Element linkAlbum)
			throws IOException {
		ArrayList<String> listaEtiquetas = new ArrayList<String>();
		Document docAlbum = Jsoup.connect(linkAlbum.attr("abs:href")).get();
		Elements links = docAlbum.select("a[href]");
		for (Element link : links) {
			String strLink = link.attr("abs:href") + link.text().trim();
			if (strLink.contains("http://www.rockdelux.com/etiquetas/")) {
				if (!link.text().equals("")) {
					System.out.println("Etiquetas: " + link.text());
					listaEtiquetas.add(link.text());
				}
			}
		}
		return listaEtiquetas;
	}

	private static void _generarCSVAlbums(ArrayList<Album> listAlbums, int numMaxEtiquetas) {
		String[][] values;
		String destinyPath = "csv/albumsRockdelux.csv";

		try {
			CSVPrinter printer = new CSVPrinter(new FileOutputStream(destinyPath));
			values = new String[listAlbums.size()][numMaxEtiquetas + 3];

			Iterator it = listAlbums.iterator();
			int row = 0;
			while (it.hasNext()) {
				int column = 0;
				Album itemAlbum = (Album) it.next();

				values[row][column] = itemAlbum.getTitulo();
				values[row][column++] = itemAlbum.getArtista();
				for (int index = 0; index <= numMaxEtiquetas; index++) {
					if (index < itemAlbum.getEtiquetas().size())
						values[row][index + column] = itemAlbum.getEtiquetas().get(index);
					else
						values[row][index + column] = "-";
				}
				values[row][numMaxEtiquetas + 2] = String.valueOf(itemAlbum.getAnyo());
				row++;
			}
			printer. println(values);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generarCSVAlbums(ArrayList<Album> listAlbums, int numMaxEtiquetas) {
		CsvWriter writer = new CsvWriter("albumsRockdelux.csv");
		
		/*
		 * Cabeceras de columnas
		 * */
		try {
			writer.write("T’tulo");
			writer.write("Artista");
			writer.write("Anyo");
			for (int i=1; i<=numMaxEtiquetas; i++) {
				writer.write("Etiqueta " + i);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Contenido
		 * */
		
		Iterator it = listAlbums.iterator();
		while (it.hasNext()) {
			Album itemAlbum = (Album) it.next();
			try {
				writer.write(itemAlbum.getTitulo());
				writer.write(itemAlbum.getArtista());
				writer.write(String.valueOf(itemAlbum.getAnyo()));
				for (int index = 0; index <= numMaxEtiquetas; index++) {
					if (index < itemAlbum.getEtiquetas().size())
						writer.write(itemAlbum.getEtiquetas().get(index));
					else
						writer.write("-");
				}
				writer.endRecord();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally { }
		}
		writer.close();
	}

}
