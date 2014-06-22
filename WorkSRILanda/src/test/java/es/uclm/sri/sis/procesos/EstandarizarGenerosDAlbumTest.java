package es.uclm.sri.sis.procesos;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.procesos.csv.TratarCSVAlbum;

public class EstandarizarGenerosDAlbumTest {

	@Test
	public void test() {
		EstandarizarGenerosDAlbum estandariza = new EstandarizarGenerosDAlbum(
				"/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_SCRAPING.csv", 
				"/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_NORMALIZADOS_TEST.csv");
		estandariza.generarAlbumsEstandar();
		ArrayList<Album> albumsCSV = TratarCSVAlbum
				.leerCVSAlbums("/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_NORMALIZADOS_TEST.csv");

		assertEquals(4272, albumsCSV.size(), 0);
	}

}
