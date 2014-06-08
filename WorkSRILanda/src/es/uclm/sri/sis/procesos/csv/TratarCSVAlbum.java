package es.uclm.sri.sis.procesos.csv;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.utilidades.Utils;

/**
 * Clase espec�fica para el tratamiento del CSV que contiene los albums.
 * 
 * @author Sergio Navarro
 * */
public final class TratarCSVAlbum {

	private static final Logger logger = Logger.getLogger(TratarCSVAlbum.class);

	private TratarCSVAlbum() {
		super();
	}

	/**
	 * @deprecated
	 * */
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
			System.out.println("Error! " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error! " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Genera el archivo CSV con los albums ya ponderados. Guarda las siguientes
	 * columnas: Album + Artista + N-Pesos de album
	 * 
	 * @parm albums: ArrayList<AlbumPonderado>
	 * @parm generos: ArrayList<String>
	 * @parm n�mero etiquetas: Int
	 * @parm destino CSV: String
	 * */
	public static void generarCSVAlbumPonderado(
			ArrayList<AlbumPonderado> listAlbums, ArrayList<String> generos,
			int numMaxEtiquetas, String destinyPath) {
		CsvWriter writer = new CsvWriter(destinyPath);
		System.out
				.println("Proceso GENERAR CSV ALBUM PONDERADO.\nAlmacenando discos...");
		int contador = 0;
		Iterator it = listAlbums.iterator();

		// Cabecera
		try {
			writer.write("Album");
			writer.write("Artista");
			Iterator itGeneros = generos.iterator();
			while (itGeneros.hasNext()) {
				writer.write(itGeneros.next().toString());
			}
			writer.endRecord();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error! " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error! " + e.getMessage());
		}

		// Datos
		while (it.hasNext()) {
			contador++;
			AlbumPonderado itemAlbum = (AlbumPonderado) it.next();
			System.out.println("Grabando album #" + contador + " de "
					+ listAlbums.size() + " - " + itemAlbum.getTitulo());
			try {
				writer.write(itemAlbum.getTitulo());
				writer.write(itemAlbum.getArtista());

				for (int index = 0; index < numMaxEtiquetas; index++) {
					writer.write(itemAlbum.getPesosGeneros()[index].toString());
				}

				writer.write(itemAlbum.getPais());
				writer.endRecord();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error! " + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error! " + e.getMessage());
			} finally {
			}
		}
		System.out.println("�Proceso finalizado!");
		writer.close();
	}

	/**
	 * Genera fichero CSV de album sin ponderar, con las etiquetas literales.
	 * Las columnas son T�tulo + Artista + Fecha + N-Tags
	 * 
	 * @parm albums: ArrayList<Album>
	 * @parm etiquetas: Int
	 * @parm destino: String
	 * @parm Logger
	 */
	public static void generarCSVAlbums(ArrayList<Album> listAlbums,
			int numMaxEtiquetas, String destinyPath, Logger logger) {
		CsvWriter writer = new CsvWriter(destinyPath);
		logger.info("Proceso GENERAR CSV ALBUMS.\nAlmacenando discos...");
		/*
		 * Cabeceras de columnas
		 */
		try {
			writer.write("T�tulo");
			writer.write("Artista");
			writer.write("Fecha");
			if (numMaxEtiquetas > 1) {
				for (int i = 1; i <= numMaxEtiquetas; i++) {
					writer.write("Etiqueta " + i);
				}
			} else
				writer.write("G�nero");
			writer.write("Pa�s");
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
				e.printStackTrace();
				logger.error("Error! " + e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error! " + e.getMessage());
			} finally {
			}
		}
		logger.info("Proceso fina");
		writer.close();
	}
	
	/**
	 * Leer fichero CSV con los datos de los albums
	 * 
	 * @parm path: String
	 * 
	 * @return albums: ArrayList<Album> 
	 * */
	public static ArrayList<Album> leerCVSAlbums(String originalyPath) {
		CsvReader reader = null;
		ArrayList<Album> listaAlbums = new ArrayList<Album>();
		try {
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
				for (int i = 3; i < numColumn - 1; i++) {
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
	
	/**
	 * Leer fichero CSV con los datos de los albums ya ponderados
	 * 
	 * @parm path: String
	 * 
	 * @return albums: ArrayList<Album> 
	 * */
	public static ArrayList<AlbumPonderado> leerCVSAlbumsPonderado(String path) {
		CsvReader reader = null;
		ArrayList<AlbumPonderado> listaAlbums = new ArrayList<AlbumPonderado>();
		try {
			reader = new CsvReader(path);
			reader.setDelimiter(',');
			while (reader.readRecord()) {
				AlbumPonderado album = new AlbumPonderado();
				Double[] arrayGeneros = new Double[18];
				int numColumn = reader.getColumnCount();
				album.setTitulo(reader.get(0));
				album.setArtista(reader.get(1));
				arrayGeneros = Utils.inicializarArrayDoble(arrayGeneros);
				for (int i = 2; i < numColumn - 1; i++) {
					double vGenero = 0.0;
					try {
						if (reader.get(i) != null && reader.get(i) != "")
							vGenero = Double.parseDouble(reader.get(i));
					} catch (NumberFormatException numExc) {

					} catch (Exception e) {

					}
					arrayGeneros[i - 2] = vGenero;
				}
				album.setGeneros(arrayGeneros);
				System.out.println(album.toString());
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
