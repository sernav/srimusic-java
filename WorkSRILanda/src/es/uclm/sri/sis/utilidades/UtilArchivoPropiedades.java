package es.uclm.sri.sis.utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class UtilArchivoPropiedades {
	
	private Properties propiedades;
	private String archivo;
	
	private UtilArchivoPropiedades(){
	}
	
	public UtilArchivoPropiedades(String archivo) {
		propiedades = new Properties();
		this.archivo = archivo;
	}
	
	public boolean cargarPropiedades() {
		try {
			propiedades.load(new FileInputStream(archivo));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<String> getPropiedades() {
		ArrayList<String> listKeys = new ArrayList<String>();
		Enumeration<Object> claves = propiedades.keys();
		while (claves.hasMoreElements()) {
			listKeys.add((String) claves.nextElement());
		}
		return listKeys;
	}
	
	public String getValorPropiedad(String propiedad) {
		return propiedades.getProperty(propiedad);
	}

}
