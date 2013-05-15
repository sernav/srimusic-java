package es.uclm.sri.sis.utilidades;


public class Utils {

	private Utils() { }
	
	public static String sustituirEspaciosBlanco(String cadena) {
		cadena = cadena.trim();
		for (int i=0; i < cadena.length(); i++) {
			if (cadena.charAt(i) == ' ') {
				cadena += '+';
			} else {
				cadena += cadena.charAt(i);
			}
		}
		return cadena;
	}
	
	public static String tratarTituloAlbum(String titulo) {
		boolean continuar = true;
		String newTitulo = "";
		titulo = titulo.trim();
		for (int i=0; i < titulo.length() && continuar; i++) {
			if (Character.isDigit(titulo.charAt(i)))
				continuar = false;
			else
				newTitulo += titulo.charAt(i);
		}
		return newTitulo;
	}
	
	public static boolean isEtiquetaCompuesta(String etiqueta) {
		if(etiqueta.equals("indietronica") ||
				etiqueta.equals("electropop")) {
			return true;
		}
		
		if (etiqueta.contains("-") && !etiqueta.equals("hip-hop")) {
			return true;
		}
		
		return false;
	}
	
	public static String[] descomponerEtiquetaCompuesta(String etiqueta) {
		String[] etiquetas = new String[2];
		if(etiqueta.equals("indietronica")) {
			etiquetas[0] = "indie";
			etiquetas[1] = "electronic";
		} else if (etiqueta.equals("electropop")){
			etiquetas[0] = "electronic";
			etiquetas[1] = "pop";
		} else if (etiqueta.contains("-") && 
				!etiqueta.equals("hip-hop") && !etiqueta.equals("singer-songwriter")) {
			if (etiqueta.split("-").length == 2)
				etiquetas = etiqueta.split("-");
		}
		return etiquetas;
	}

}
