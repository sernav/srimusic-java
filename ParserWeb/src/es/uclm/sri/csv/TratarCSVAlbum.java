package es.uclm.sri.csv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import es.uclm.sri.entidades.Album;

public final class TratarCSVAlbum {
	
	public static void main(String[] args) {
		leerCVSAlbums("/Users/sergionavarro/PFC/CSV_Albums_Lastfm.csv");
	}

	private TratarCSVAlbum() {
		super();
	}

	public static void _generarCSVAlbums(ArrayList<Album> listAlbums,
			int numMaxEtiquetas, String destinyPath, Logger logger) {
		String[][] values;

		try {
			CSVPrinter printer = new CSVPrinter(new FileOutputStream(
					destinyPath));
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
						values[row][index + column] = itemAlbum.getEtiquetas()
								.get(index);
					else
						values[row][index + column] = "-";
				}
				values[row][numMaxEtiquetas + 2] = String.valueOf(itemAlbum
						.getFecha());
				row++;
			}
			printer.println(values);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generarCSVAlbums(ArrayList<Album> listAlbums,
			int numMaxEtiquetas, String destinyPath, Logger logger) {
		CsvWriter writer = new CsvWriter(destinyPath);
		logger.info("Almacenando discos...");
		/*
		 * Cabeceras de columnas
		 */
		try {
			writer.write("T’tulo");
			writer.write("Artista");
			writer.write("Fecha");
			if (numMaxEtiquetas > 1) {
				for (int i = 1; i <= numMaxEtiquetas; i++) {
					writer.write("Etiqueta " + i);
				}
			} else
				writer.write("GŽnero");
			writer.write("Pa’s");
			writer.endRecord();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * Contenido
		 */
		int contador = 0;
		Iterator it = listAlbums.iterator();
		while (it.hasNext()) {
			contador++;
			Album itemAlbum = (Album) it.next();
			logger.info("Grabando album #" + contador + " de "
					+ listAlbums.size() + " - " + itemAlbum.getTitulo());
			try {
				writer.write(itemAlbum.getTitulo());
				writer.write(itemAlbum.getArtista());
				writer.write(String.valueOf(itemAlbum.getFecha()));
				if (numMaxEtiquetas > 1) {
					for (int index = 0; index < numMaxEtiquetas; index++) {
						if (index < itemAlbum.getEtiquetas().size())
							writer.write(itemAlbum.getEtiquetas().get(index));
					}
				} else {
					writer.write(itemAlbum.getEtiquetas().get(0));
				}
				writer.write(itemAlbum.getPais());
				writer.endRecord();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("Error! " + e.getMessage());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("Error! " + e.getMessage());
			} finally {
			}
		}
		logger.info("Proceso concluido");
		writer.close();
	}
	
	public static ArrayList<Album> leerCVSAlbums(String originalyPath) {
		CsvReader reader = null;
		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		try{
			reader = new CsvReader(originalyPath);
			reader.setDelimiter(',');
			while (reader.readRecord()) {
				Album album = new Album();
				ArrayList<String> listaEtq = new ArrayList<String>();
				int numColumn = reader.getColumnCount();
				album.setTitulo(reader.get(0));
				album.setArtista(reader.get(1));
				album.setFecha(reader.get(2));
				album.setPais("");
				for (int i=3; i < numColumn-1; i++) {
					if (!reader.get(i).equals("default"))
						listaEtq.add(reader.get(i));
				}
				album.setEtiquetas(listaEtq);
				listaAlbums.add(album);
			}
		} catch (IOException e) {
			System.out.println("Error de I/O!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		
		return listaAlbums;
	}

}
