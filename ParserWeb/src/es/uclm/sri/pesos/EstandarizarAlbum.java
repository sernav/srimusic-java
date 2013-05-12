package es.uclm.sri.pesos;

import java.util.ArrayList;
import java.util.StringTokenizer;

import es.uclm.sri.csv.TratarCSVAlbum;
import es.uclm.sri.entidades.Album;
import es.uclm.sri.utilidades.UtilArchivoPropiedades;
import es.uclm.sri.utilidades.Utils;

public class EstandarizarAlbum {
	
	private static UtilArchivoPropiedades properties;
	
	public static void main(String[] args) {
		recogeAlbumsEstandar();
	}
	
	protected static void recogeAlbumsEstandar() {
		ArrayList<Album> listaAlbums = TratarCSVAlbum.leerCVSAlbums("/Users/sergionavarro/PFC/CSV_Albums/CSV_Albums_Lastfm_Sabado4.csv");
		try {
			properties = new UtilArchivoPropiedades("src/es/uclm/sri/generoEstandar.properties");
			properties.cargarPropiedades();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Album album : listaAlbums) {
			album.setEtiquetas(estandarizarGeneros(album));
		}
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
