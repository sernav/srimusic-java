package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import weka.core.Instance;
import weka.core.Instances;

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
	
	public static HashMap<Instance, Double> sortHashIntances(HashMap<Instance, Double> hashInstances) {
        HashMap<Instance, Double> mapResultado = new LinkedHashMap<Instance, Double>();
        List<Instance> mapKeys = new ArrayList<Instance>(hashInstances.keySet());
        List mapValues = new ArrayList(hashInstances.values());
        TreeSet<Double> conjuntoOrdenado = new TreeSet<Double>(mapValues);
        
        Object[] arrayOrdenado = conjuntoOrdenado.toArray();
        int size = arrayOrdenado.length;
        for (int i = 0; i < size; i++) {
	        mapResultado.put(mapKeys.get(mapValues.indexOf(arrayOrdenado[i])),(Double)arrayOrdenado[i]);
        }
        return mapResultado;
	}

}
