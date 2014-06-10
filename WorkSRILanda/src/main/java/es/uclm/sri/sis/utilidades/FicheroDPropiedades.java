package main.java.es.uclm.sri.sis.utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import main.java.es.uclm.sri.sis.KSistema;

public class FicheroDPropiedades {
	
	private Properties propiedades;
	private String archivo;
	
	private FicheroDPropiedades(){
		super();
	}
	
	public FicheroDPropiedades(String archivo) {
		propiedades = new Properties();
		this.archivo = archivo;
	}
	
	public boolean cargarPropiedades() {
		try {
			propiedades.load(new FileInputStream(KSistema.Recursos.PATH_GENEROS_ESTANDAR_PROPERTIES));
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
