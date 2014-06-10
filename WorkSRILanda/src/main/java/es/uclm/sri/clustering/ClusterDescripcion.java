package main.java.es.uclm.sri.clustering;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Módulo clustering: Lista de nombres y valores que describen el cluster
 * 
 * @author Sergio Navarro
 * @version 1.0
 */
public class ClusterDescripcion {

	private LinkedHashMap<String, Double> attributes = null;
	private final int defaultSize = 16;
	private final float floatFactor = 0.75F;
	private final boolean accessOrder = false;

	/**
	 * Constructor por defecto
	 */
	public ClusterDescripcion() {
		attributes = new LinkedHashMap<String, Double>(defaultSize, floatFactor, accessOrder);
	}

	/**
	 * Constructor especificando número de atributos del cluster
	 */
	public ClusterDescripcion(int numAttributes) {
		attributes = new LinkedHashMap<String, Double>(numAttributes, floatFactor, accessOrder);
	}

	/**
	 * Devuelve el nombre de los atributos del cluster
	 * 
	 * @return atributos: Iterator
	 */
	public Iterator<String> getAttributeNames() {
		return attributes.keySet().iterator();
	}

	/**
	 * Devuelve el valor de los atributos del cluster según su key
	 * 
	 * @param key: String
	 * @return valor del atributo: double
	 */
	public double getValue(String key) {
		Double temp = attributes.get(key);
		return temp.doubleValue();
	}
}
