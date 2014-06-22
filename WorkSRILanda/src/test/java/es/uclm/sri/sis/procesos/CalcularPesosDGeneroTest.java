package es.uclm.sri.sis.procesos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.procesos.csv.TratarCSVAlbum;

public class CalcularPesosDGeneroTest {

	@Test
	public void test() {
		CalcularPesosDGenero calculadora = new CalcularPesosDGenero(
				"/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_NORMALIZADOS_TEST.csv",
				"/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_PONDERADOS_TEST.csv");
		calculadora.procesoDCalculo();

		ArrayList<Album> albumsCSV = TratarCSVAlbum
				.leerCVSAlbums("/Users/sergionavarro/Google Drive/PFC/Trabajo de campo/CSV_Albums/test/ALBUMS_PONDERADOS_TEST.csv");
		
		assertEquals(4272, albumsCSV.size(), 0);
	}

}
