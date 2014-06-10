package main.java.es.uclm.sri.sis.procesos;

import java.util.ArrayList;

import main.java.es.uclm.sri.persistencia.admon.AdmonPesosAlbum;
import main.java.es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;
import main.java.es.uclm.sri.sis.entidades.AlbumPonderado;
import main.java.es.uclm.sri.sis.procesos.csv.TratarCSVAlbum;

/**
 * Almacena albums ponderados desde un fichero csv
 * 
 * @author Sergio Navarro
 * */
public class AlmacenarAlbumsPonderado {
	
	private static String path;
	
	/**
	 * Constructor con la ruta del fichero csv que contiene los albums ponderados
	 * */
	public AlmacenarAlbumsPonderado(String path) {
		this.path = path;
	}
	
	/**
	 * Función principal de la clase. Utiliza la clase admon de pesos de album <code>AdmonPesosAlbum</code>
	 * 
	 * */
	public void loadAlbumsPonderadosToBD() {
		AdmonPesosAlbum admonPesosAlbum = new AdmonPesosAlbum();
		
		ArrayList<Pesosalbum> lPesos = getListaPesosDAlbum(path);
		for (Pesosalbum pesos : lPesos) {
			try {
				admonPesosAlbum.insertarPesosAlbum(pesos);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Genera lista de pesos para cada uno de los albums del fichero csv
	 * 
	 * @param path fichero csv
	 * @return ArrayList<Pesosalbum>
	 * */
	protected static ArrayList<Pesosalbum> getListaPesosDAlbum (String path) {
		ArrayList<AlbumPonderado> lAlbums = TratarCSVAlbum.leerCVSAlbumsPonderado(path);
		ArrayList<Pesosalbum> lPesos = new ArrayList<Pesosalbum>();
		for (AlbumPonderado album : lAlbums) {
			Pesosalbum pesos = new Pesosalbum();
			pesos.setALBUM(album.getTitulo());
			pesos.setARTISTA(album.getArtista());
			Double[] pesosAlbum = album.getPesosGeneros();
			anyadirValorPesos(pesos, pesosAlbum);
			
			lPesos.add(pesos);
		}
		return lPesos;
	}
	
	private static void anyadirValorPesos(Pesosalbum pesos, Double[] pesosAlbum) {
		pesos.setROCK(pesosAlbum[0]);
		pesos.setINDIE(pesosAlbum[1]);
		pesos.setALTERNATIVE(pesosAlbum[2]);
		pesos.setPOP(pesosAlbum[3]);
		pesos.setELECTRONIC(pesosAlbum[4]);
		pesos.setBRIT(pesosAlbum[5]);
		pesos.setFUNK(pesosAlbum[6]);
		pesos.setRAP(pesosAlbum[7]);
		pesos.setFOLK(pesosAlbum[8]);
		pesos.setSINGER(pesosAlbum[9]);
		pesos.setHEAVY(pesosAlbum[10]);
		pesos.setBLUES(pesosAlbum[11]);
		pesos.setGRUNGE(pesosAlbum[12]);
		pesos.setCLASSIC(pesosAlbum[13]);
		pesos.setPUNK(pesosAlbum[14]);
		pesos.setINSTRUMENTAL(pesosAlbum[15]);
		pesos.setAMBIENT(pesosAlbum[16]);
		pesos.setREGGAE(pesosAlbum[17]);
	}

}
