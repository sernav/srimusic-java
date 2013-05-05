package es.uclm.sri.utils;


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

}
