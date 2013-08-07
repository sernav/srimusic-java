package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

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
	
	public static Instance[] estabilizarAtributosNaN(Instance[] instances) {
		Instance[] aux = new Instance[instances.length];
		for (int i = 0; i < instances.length; i++) {
			aux[i] = estabilizarAtributosNaN(instances[i]);
		}
		return aux;
	}
	
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
	
	public static Instance _descartarTituloYArtista(Instance instance) {
		Enumeration<Attribute> enumAtts = instance.enumerateAttributes();
		while (enumAtts.hasMoreElements()) {
			Attribute att = enumAtts.nextElement();
			if (!att.isNumeric()) {
				instance. setMissing(att);
			}
		}
		return instance;
	}

}
