package es.uclm.sri.clustering;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Lista de nombres y valores que describen el cluster
 * 
 * @author sernav
 * @version 1.0
 * @since 1.0
 * 
 */
public class ClusterDescripcion {

	private LinkedHashMap<String, Double> attributes = null;
	// set some defaults for the linked hash map - see java doc for details
	private final int defaultSize = 16;
	private final float floatFactor = 0.75F;
	private final boolean accessOrder = false; // insertion order!

	/**
	 * Default constructor
	 */
	public ClusterDescripcion() {
		attributes = new LinkedHashMap<String, Double>(defaultSize,
				floatFactor, accessOrder);
	}

	/**
	 * Constructor which specifies the number of attributes in this cluster
	 */
	public ClusterDescripcion(int numAttributes) {
		attributes = new LinkedHashMap<String, Double>(numAttributes,
				floatFactor, accessOrder);
	}

	/**
	 * Returns an Iterator of the attribute names.
	 * 
	 * @return
	 */
	public Iterator<String> getAttributeNames() {
		return attributes.keySet().iterator();
	}

	/**
	 * Returns the value of the object identified by the key.
	 * 
	 * @param key
	 * @return
	 */
	public double getValue(String key) {
		Double temp = attributes.get(key);
		return temp.doubleValue();
	}
}
