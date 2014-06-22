package es.uclm.sri.sis.procesos;

import java.util.Calendar;

import es.uclm.sri.sis.utilidades.Utils;

/**
 * Procesador (Facade) para el tratamiento de los albums conseguidos mediante el Scraping
 * Almacena los resultados en el directorio /temp.
 * 
 * @author Sergio Navarro
 * */
public class ProcesadorDAlbumsFacade {
	
	private static String pathCsvAlbums;
	private static boolean loadBD;
	
	/**
	 * Constructor público. Requiere la ruta con el origen de los datos.
	 * 
	 * @param pathCsvAlbums
	 * 			Ruta con los albums originales
	 * @param loadBD
	 * 			Indica si los resultados se almacenan en BD (PESOSALBUM)
	 * */
	public ProcesadorDAlbumsFacade(String pathCsvAlbums, boolean loadBD) {
		this.pathCsvAlbums = pathCsvAlbums;
		this.loadBD = loadBD;
	}
	
	/**
	 * Función principal que invoca a la clase que estandariza los géneros de album <code>EstandarizarGenerosDAlbum</code>,
	 * después invoca a la clase que calcula los pesos de géneros <code>CalcularPesosDGenero</code> y,
	 * si el atributo loadBD es "true", se almacenan el BD con la clase <code>AlmacenarAlbumsPonderado</code>
	 * 
	 * */
	public void execute() {
		String destinoCsv = getDestinyPath();
		// Albums originales a albums con generos estándar de sistema
		EstandarizarGenerosDAlbum estandariza = new EstandarizarGenerosDAlbum(getPathAlbums(), destinoCsv);
		estandariza.generarAlbumsEstandar();
		
		//Albums con géneros estándar a albums ponderados
		CalcularPesosDGenero calculaPesos = new CalcularPesosDGenero(destinoCsv, destinoCsv);
		calculaPesos.procesoDCalculo();
		
		//SI queremos almacenar en base de datos los albums ponderados en el fichero CSV
		if (loadBD) {
			AlmacenarAlbumsPonderado almacenador = new AlmacenarAlbumsPonderado(destinoCsv);
			almacenador.loadAlbumsPonderadosToBD();
		}
	}
	
	public String getPathAlbums() {
		return this.pathCsvAlbums;
	}
	
	/**
	 * Ruta donde se van almacenando los ficheros csv con los resultados del procesador
	 * 
	 * @param ruta
	 * */
	protected String getDestinyPath() {
		String csv = ".csv";
        String ext = Utils.convertirFecha(Calendar.getInstance().getTime(), "ddMMyyyy");
        String path = "temp/proceso_albums_" + ext + csv;
        
        return path;
	}

}
