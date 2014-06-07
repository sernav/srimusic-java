package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import es.uclm.sri.sis.utilidades.ValueComparator;

/**
 * Herramientas comunes para la aplicación de Weka en el sistema.
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class WekaUtilities {

	public WekaUtilities() {
		super();
	}
	
	public static Instances eliminarAtributo(Instances data) {
		Enumeration<Instance> enumn = data.enumerateAttributes();

		while (enumn.hasMoreElements()) {
			Instance instance = enumn.nextElement();
		}
		return null;
	}
	
	/**
	 * Ordenar instancias Weka por valor
	 * 
	 * @param HashMap<Instance, Double> hashInstances
	 * @return HashMap<Instance, Double>
	 * */
	public static HashMap<Instance, Double> sortHashIntances(
			HashMap<Instance, Double> hashInstances) {
		HashMap<Instance, Double> mapResultado = new LinkedHashMap<Instance, Double>();
		List<Instance> mapKeys = new ArrayList<Instance>(hashInstances.keySet());
		List mapValues = new ArrayList(hashInstances.values());
		TreeSet<Double> conjuntoOrdenado = new TreeSet<Double>(mapValues);

		Object[] arrayOrdenado = conjuntoOrdenado.toArray();
		int size = arrayOrdenado.length;
		for (int i = 0; i < size; i++) {
			mapResultado.put(mapKeys.get(mapValues.indexOf(arrayOrdenado[i])),
					(Double) arrayOrdenado[i]);
		}
		return mapResultado;
	}
	
	/**
	 * Ordenar instancias WekaSRIInstance por valor
	 * 
	 * @param HashMap<Instance, Double> hashInstances
	 * @return HashMap<Instance, Double>
	 * */
	public static HashMap<WekaSRIInstance, Double> sortHashWekaIntances(
			HashMap<WekaSRIInstance, Double> hashInstances) {
		HashMap<WekaSRIInstance, Double> mapResultado = new LinkedHashMap<WekaSRIInstance, Double>();
		List<WekaSRIInstance> mapKeys = new ArrayList<WekaSRIInstance>(
				hashInstances.keySet());
		List mapValues = new ArrayList(hashInstances.values());
		TreeSet<Double> conjuntoOrdenado = new TreeSet<Double>(mapValues);

		Object[] arrayOrdenado = conjuntoOrdenado.toArray();
		for (int i = 0; i < arrayOrdenado.length; i++) {
			mapResultado.put(mapKeys.get(mapValues.indexOf(arrayOrdenado[i])),
					(Double) arrayOrdenado[i]);
		}
		return mapResultado;
	}
	
	/**
	 * Ordenar instancias WekaSRIInstance por valor V2
	 * 
	 * @param HashMap<Instance, Double> hashInstances
	 * @return HashMap<Instance, Double>
	 * */
	public static HashMap<WekaSRIInstance, Double> sortHashWekaIntancesV2(
			HashMap<WekaSRIInstance, Double> hashInstances) {
		HashMap<WekaSRIInstance, Double> mapResultado = new LinkedHashMap<WekaSRIInstance, Double>();
		List<WekaSRIInstance> mapKeys = new ArrayList<WekaSRIInstance>(
				hashInstances.keySet());
		List mapValues = new ArrayList(hashInstances.values());
		ArrayList<Double> arrayValues = new ArrayList<Double>();
		Iterator<Double> it = mapValues.iterator();
		int diferencial = 1;
		while(it.hasNext()) {
			Double d = it.next();
			d = d + (diferencial * 0.0000000001); //Necesidad del sistema de reglas.
			arrayValues.add(d);
			diferencial++;
		}
		
		mapValues = arrayValues;
		
		Double[] dArr = (Double[]) mapValues.toArray(new Double[mapValues.size()]);
		Arrays.sort(dArr);
		
		for (int i = 0; i < dArr.length; i++) {
			mapResultado.put(mapKeys.get(mapValues.indexOf(dArr[i])),
					(Double) dArr[i]);
		}
		
		return mapResultado;
	}
	
	/**
	 * Ordenar instancias WekaSRIInstance por valor V3
	 * 
	 * @param HashMap<Instance, Double> hashInstances
	 * @return HashMap<Instance, Double>
	 * */
	public static HashMap<WekaSRIInstance, Double> sortHashWekaIntancesV3(
			HashMap<WekaSRIInstance, Double> hashInstances) {
		HashMap<WekaSRIInstance, Double> map = new HashMap<WekaSRIInstance, Double>();
		ValueComparator bvc = new ValueComparator(map);
		TreeMap<WekaSRIInstance, Double> sorted_map = new TreeMap(bvc);

		System.out.println("unsorted map");
		for (WekaSRIInstance key : map.keySet()) {
			System.out.println("key/value: " + key + "/" + map.get(key));
		}

		sorted_map.putAll(map);

		System.out.println("results");
		for (WekaSRIInstance key : sorted_map.keySet()) {
			System.out.println("key/value: " + key + "/" + sorted_map.get(key));
		}

		return map;
	}
	
	/**
	 * Elimina los valores NaN y los sutituye por el valor 0.0
	 * 
	 * @param Instance
	 * @return Instance
	 * */
	public static Instance estabilizarAtributosNaN(Instance instance) {
		double[] attValues = instance.toDoubleArray();
		for (int i = 0; i < attValues.length; i++) {
			Double d = new Double(attValues[i]);
			if (d.isNaN()) {
				attValues[i] = 0.0;
			}
		}
		return new Instance(1.0, attValues);
	}
	
	/**
	 * Elimina los valores NaN de un vector de objetos Instance y los sutituye por el valor 0.0
	 * 
	 * @param Instance[]
	 * @return Instance[]
	 * */
	public static Instance[] estabilizarAtributosNaN(Instance[] instances) {
		Instance[] aux = new Instance[instances.length];
		for (int i = 0; i < instances.length; i++) {
			aux[i] = estabilizarAtributosNaN(instances[i]);
		}
		return aux;
	}
	
	/**
	 * 
	 * */
	public static Instance descartarTituloYArtista(Instance instance) {
		Instance aux = instance;
		if (aux.attribute(0).name().equals("ALBUM")) {
			if (aux.attribute(1).name().equals("ARTISTA")) {
				aux.dataset().deleteAttributeAt(0);
				aux.dataset().deleteAttributeAt(0);
			}
		}
		return aux;
	}
	
	/**
	 * Estable atributos de una instancia Weka.
	 * 
	 * @param Instance
	 * @param argumentos: String[]
	 * 
	 * @return Instance
	 * */
	public static Instance getOutAttributes(Instance instance, String[] args) {
		Enumeration<Attribute> enumAtt = instance.enumerateAttributes();
		while (enumAtt.hasMoreElements()) {
			Attribute att = enumAtt.nextElement();
			for (int i = 0; i < args.length; i++) {
				if (att.name().equals(args[i])) {
					instance.dataset().deleteAttributeAt(i);
				}
			}
		}
		return instance;
	}

	/**
	 * Estable atributos no numéricos de una instancia Weka.
	 * 
	 * @param Instance
	 * @param argumentos: String[]
	 * 
	 * @return Instance
	 * */
	public static Instance getOutAttributesNoNumeric(Instance instance) {
		Enumeration<Attribute> enumAtts = instance.enumerateAttributes();
		while (enumAtts.hasMoreElements()) {
			Attribute att = enumAtts.nextElement();
			if (!att.isNumeric()) {
				instance.setMissing(att);
			}
		}
		return instance;
	}

}
