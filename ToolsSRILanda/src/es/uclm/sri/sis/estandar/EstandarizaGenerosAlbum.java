package es.uclm.sri.sis.estandar;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import es.uclm.sri.sis.entidades.Album;
import es.uclm.sri.sis.operaciones.csv.TratarCSVAlbum;
import es.uclm.sri.sis.utilidades.UtilArchivoPropiedades;
import es.uclm.sri.sis.utilidades.Utils;

public class EstandarizaGenerosAlbum {
	
	private static UtilArchivoPropiedades properties;
	
	private static final Logger logger = Logger
			.getLogger(EstandarizaGenerosAlbum.class);
	
	private static String destinyPath = "/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Norm.csv";
	private static String origProperties = "src/es/uclm/sri/properties/generoEstandar.properties";
	
	public static void main(String[] args) {
		recogeAlbumsEstandar();
	}
	
	protected static void recogeAlbumsEstandar() {
		ArrayList<Album> listaAlbums = TratarCSVAlbum.leerCVSAlbums("/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Lastfm_Sabado4.csv");
		ArrayList<Album> listaAlbumsNormalizado = new ArrayList<Album>();
		try {
			properties = new UtilArchivoPropiedades(origProperties);
			properties.cargarPropiedades();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int index = 1; index < listaAlbums.size(); index++) {
			Album album = listaAlbums.get(index);
			album.setEtiquetas(estandarizarGeneros(album));
			album.setPais("");
			System.out.println("Album Normalizado: " + album.toString());
			listaAlbumsNormalizado.add(album);
		}
		TratarCSVAlbum.generarCSVAlbums(listaAlbumsNormalizado, 18, destinyPath, logger);
	}
	
	protected static ArrayList<String> estandarizarGeneros(Album album) {
		ArrayList<String> listaEtq = album.getEtiquetas();
		ArrayList<String> listaEtqSimple = new ArrayList<String>();
		for (int i=0; i < listaEtq.size(); i++) {
			if(Utils.isEtiquetaCompuesta(listaEtq.get(i))) {
				String[] etqSimples = Utils.descomponerEtiquetaCompuesta(listaEtq.get(i));
				for (int j=0; j < etqSimples.length; j++) {
					String etqStandar = estandarizaGeneroSimple(etqSimples[j]);
					if (etqStandar != "")
						listaEtqSimple.add(etqStandar);
				}
			} else {
				String etqStandar = estandarizaGeneroSimple(listaEtq.get(i));
				if (etqStandar != "")
					listaEtqSimple.add(etqStandar);
			}
		}
		return listaEtqSimple;
	}
	
	private static String estandarizaGeneroSimple(String genero) {
		String generoStdr = "";
		StringTokenizer crud = null;
		ArrayList<String> listKeyProp = new ArrayList<String>();
		listKeyProp = properties.getPropiedades();
		
		boolean isGeneroStd = false;
		
		for(int i=0; i < listKeyProp.size() && !isGeneroStd; i++) {
			String valorProp = properties.getValorPropiedad(listKeyProp.get(i));
			crud = new StringTokenizer(valorProp, ",");
			if (isGeneroDListaEstandar(crud, genero)) {
				String alistKeyProp = listKeyProp.get(i);
				alistKeyProp.split(".");
				generoStdr = listKeyProp.get(i);
				isGeneroStd = true;
			}
		}
		
		return generoStdr;
	}
	
	private static boolean isGeneroDListaEstandar(StringTokenizer crud, String genero) {
		while(crud.hasMoreElements()) {
			if(genero.equals(crud.nextToken())) {
				return true;
			}
		}
		return false;
	}

}

