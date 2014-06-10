package main.java.es.uclm.sri.clustering.weka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import main.java.es.uclm.sri.clustering.IDatosCluster;

/**
 * Implementación de la interfaz de datos de cluster
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class WekaDatosCluster implements IDatosCluster, Serializable {

	private static final long serialVersionUID = 2898797823766558128L;
	private Instances instances = null;
	private int vectors = 0;
	private int dimensions = 0;
	private double maximum = 0.0;
	private boolean normalize = false;
	private double ranges[][] = null;

	/**
	 * Constructor protegido
	 * 
	 * @param name
	 * 			Nombre del cluster
	 * @param attributes
	 * 			Lista de atributos como cadena.
	 * @param vectors
	 * 			Vector de datos
	 * @param dimensions
	 * 			Dimensión del vector
	 * @param classAttributes
	 * 			Tipos de atributos (nominales, numéricos,...)
	 */
	WekaDatosCluster(String name, List<String> attributes, int vectors,
			int dimensions, List<String> classAttributes) {
		this.vectors = vectors;
		this.dimensions = dimensions;
		FastVector fastAttributes = new FastVector(attributes.size());
		for (int i = 0; i < attributes.size(); i++) {
			String attr = attributes.get(i);
			fastAttributes.addElement(new Attribute(attr));
		}
		Attribute tmp = null;
		if (classAttributes != null) {
			String attr = "class";
			FastVector vect = new FastVector(classAttributes.size());
			for (String tmpClass : classAttributes) {
				vect.addElement(tmpClass);
			}
			tmp = new Attribute(attr, vect);
			fastAttributes.addElement(tmp);
		}

		instances = new Instances(name, fastAttributes, vectors);

		if (classAttributes != null) {
			instances.setClass(tmp);
			for (int i = 0; i < vectors; i++) {
				Instance tmpInst = new Instance(fastAttributes.size());
				tmpInst.setDataset(instances);
				for (int j = 0; j < dimensions; j++) {
					tmpInst.setValue(j, 0.0);
				}
				instances.add(tmpInst);
			}
		} else {
			for (int i = 0; i < vectors; i++) {
				double[] values = new double[dimensions];
				for (int j = 0; j < dimensions; j++) {
					values[j] = 0.0;
				}
				instances.add(new Instance(1.0, values));
			}
		}
	}

	/**
	 * Constructor partiendo de instancias de Weka.
	 * 
	 * @param instances
	 */
	public WekaDatosCluster(Instances instances) {
		this.instances = instances;
		this.vectors = instances.numInstances();
		if (this.vectors > 0)
			this.dimensions = instances.instance(0).numAttributes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#addValue(int, int, double)
	 */
	public void addValue(int vectorIndex, int dimensionIndex, double value) {
		Instance i = instances.instance(vectorIndex);
		i.setValue(dimensionIndex, value);
		if (maximum < value)
			maximum = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getValue(int, int)
	 */
	public double getValue(int vectorIndex, int dimensionIndex) {
		if (normalize) {
			double tmp = instances.instance(vectorIndex).value(dimensionIndex);
			// restar el valor mínimo
			tmp = tmp - ranges[dimensionIndex][0];
			// divide por el rango
			tmp = tmp / ranges[dimensionIndex][1];
			return tmp;
		} else {
			return instances.instance(vectorIndex).value(dimensionIndex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getManhattanDistance(int, int)
	 */
	public double getManhattanDistance(int firstVector, int secondVector) {
		// SI firstVector >= 0 && firstVector < vectors : firstVector;
		// SI secondVector >= 0 && secondVector < vectors : secondVector;
		double distance = 0.0;
		for (int i = 0; i < dimensions; i++) {
			distance += Math.abs(instances.instance(firstVector).value(i)
					- instances.instance(secondVector).value(i));
		}
		return distance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getCartesianDistance(int, int)
	 */
	public double getCartesianDistance(int firstVector, int secondVector) {
		// SI firstVector > 0 && firstVector < vectors : firstVector;
		// SI secondVector > 0 && secondVector < vectors : secondVector;
		double distance = 0.0;
		for (int i = 0; i < dimensions; i++) {
			double tmp = Math.abs(instances.instance(firstVector).value(i)
					- instances.instance(secondVector).value(i));
			distance += tmp * tmp;
		}
		return Math.sqrt(distance);
	}
	
	/**
	 * Calcula la distancia Euclídea entre dos elementos de un vector:
	 * 	dE = [(Pi - Qi)^2] ^ 1/2
	 * 
	 * @param firstVector
	 * 			Elemento Pi
	 * @param secondVector
	 * 			Elemento Qi
	 * @return dE
	 * */
	public double getDistanciaEuclidea(int firstVector, int secondVector) {
		Double x = Double.parseDouble(Integer.toString(firstVector));
		Double y = Double.parseDouble(Integer.toString(secondVector));
		double sum = Math.pow(x.doubleValue() - y.doubleValue(), 2);

		return Math.sqrt(sum);
	}
	
	/**
	 * Calcula la distancia Euclídea entre dos vectores:
	 * 	dE = ∑[(Pi - Qi)^2] ^ 1/2
	 * 
	 * @param x
	 * 			Vector de valores decimales P
	 * @param y
	 * 			Vector de valores decimales Q
	 * @return dE
	 * */
	public double getDistanciaEuclidea(double[] x, double[] y) {
		double sum = 0;
		boolean isDe = false;
		for (int i = 0; i < Math.min(x.length, y.length); i++) {
			Double dx = new Double(x[i]);
			Double dy = new Double(y[i]);
			// Si el vector tiene valores double.isNaN, se le atribuye un 0
			if(dx.isNaN()) {
				x[i] = 0.0;
			}
			if(dy.isNaN()) {
				y[i] = 0.0;
			}
			if (x[i] > 0 || y[i] > 0) {
				sum += Math.pow(x[i] - y[i], 2);
				isDe = true;
			}
		}
		if (isDe) {
			return Math.sqrt(sum);
		} else {
			return 1.0;
		}
		
	}
	
	/**
	 * Calcula la distancia Euclídea entre dos vectores con los CENTROIDES del clusterin
	 * 	dE = ∑[(Pi - Qi)^2] ^ 1/2
	 * 
	 * @param x
	 * 			Vector de valores decimales P
	 * @param y
	 * 			Vector de valores decimales Q
	 * @return dE
	 * */
	public double getDistanciaEuclideaCentroide(double[] x, double[] y) {
		double sum = 0;
		boolean isDe = false;
		for (int i = 0; i < Math.min(x.length, y.length); i++) {
			Double dx = new Double(x[i]);
			Double dy = new Double(y[i]);
			if(dx.isNaN()) {
				x[i] = 0.0;
			}
			if(dy.isNaN()) {
				y[i] = 0.0;
			}
			if (x[i] != 0 && y[i] != 0) {
				sum += Math.pow(x[i] - y[i], 2);
				isDe = true;
			}
		}
		if (isDe) {
			return Math.sqrt(sum);
		} else {
			return 1.0;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getData()
	 */
	public Object getData() {
		return instances;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getEventNames()
	 */
	public List<String> getEventNames() {
		Enumeration<Attribute> e = instances.enumerateAttributes();
		List<String> names = new ArrayList<String>(
				instances.numDistinctValues(0));
		while (e.hasMoreElements()) {
			Attribute tmp = (Attribute) e.nextElement();
			names.add(tmp.name());
		}
		return names;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#numVectors()
	 */
	public int numVectors() {
		return vectors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#numDimensions()
	 */
	public int numDimensions() {
		return dimensions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getName()
	 */
	public String getName() {
		return this.instances.relationName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getMaximum()
	 */
	public double getMaximum() {
		return maximum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getVector()
	 */
	public double[] getVector(int i) {
		double[] data = new double[dimensions];
		for (int j = 0; j < dimensions; j++) {
			data[j] = instances.instance(i).value(j);
		}
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getCorrelation()
	 */
	public double getCorrelation(int x, int y) {
		double r = 0.0;
		double xAvg = 0.0;
		double yAvg = 0.0;
		double xStDev = 0.0;
		double yStDev = 0.0;
		// double sum = 0.0;

		for (int i = 0; i < vectors; i++) {
			xAvg += instances.instance(i).value(x);
			yAvg += instances.instance(i).value(y);
		}

		// Buscar la media del primer vector (parámetro x)
		xAvg = xAvg / vectors;
		// Buscar la media del segundo vector (parámetro y)
		yAvg = yAvg / vectors;

		for (int i = 0; i < vectors; i++) {
			xStDev += (instances.instance(i).value(x) - xAvg)
					* (instances.instance(i).value(x) - xAvg);
			yStDev += (instances.instance(i).value(y) - yAvg)
					* (instances.instance(i).value(y) - yAvg);
		}

		// Buscar la Desviación Estándar del primer vector (parámetro x)
		xStDev = xStDev / (vectors - 1);
		xStDev = Math.sqrt(xStDev);
		// Buscar la Desviación Estándar del segundo vector (parámetro y)
		yStDev = yStDev / (vectors - 1);
		yStDev = Math.sqrt(yStDev);

		// Solución:
		double tmp1 = 0.0;
		double tmp2 = 0.0;
		for (int i = 0; i < vectors; i++) {
			tmp1 = (instances.instance(i).value(x) - xAvg) / xStDev;
			tmp2 = (instances.instance(i).value(y) - yAvg) / yStDev;
			r += tmp1 * tmp2;
		}
		r = r / (vectors - 1);

		 System.out.println("Avg(x) = " + xAvg + ", Avg(y) = " + yAvg);
		 System.out.println("DesStd(x) = " + xStDev + ", DesStd(y) = " + yStDev);
		 System.out.println("r = " + r);

		return r;
	}

	private double distance(Instance first, Instance second) {

		double distance = 0;
		int firstI, secondI;

		for (int p1 = 0, p2 = 0; p1 < first.numValues()
				|| p2 < second.numValues();) {
			if (p1 >= first.numValues()) {
				firstI = first.numAttributes();
			} else {
				firstI = first.index(p1);
			}
			if (p2 >= second.numValues()) {
				secondI = second.numAttributes();
			} else {
				secondI = second.index(p2);
			}

			double diff;
			if (firstI == secondI) {
				diff = difference(firstI, first.valueSparse(p1),
						second.valueSparse(p2));
				p1++;
				p2++;
			} else if (firstI > secondI) {
				diff = difference(secondI, 0, second.valueSparse(p2));
				p2++;
			} else {
				diff = difference(firstI, first.valueSparse(p1), 0);
				p1++;
			}
			distance += diff * diff;
		}

		// return Math.sqrt(distance / m_ClusterCentroids.numAttributes());
		return distance;
	}

	private double difference(int index, double val1, double val2) {
		// Distáncia matemática. Solo atribútos numéricos.
		if (Instance.isMissingValue(val1) || Instance.isMissingValue(val2)) {
			if (Instance.isMissingValue(val1) && Instance.isMissingValue(val2)) {
				return 1;
			} else {
				double diff;
				if (Instance.isMissingValue(val2)) {
					diff = norm(val1, index);
				} else {
					diff = norm(val2, index);
				}
				if (diff < 0.5) {
					diff = 1.0 - diff;
				}
				return diff;
			}
		} else {
			return norm(val1, index) - norm(val2, index);
		}
	}
	
	private double norm(double x, int i) {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#addMainValue()
	 */
	public void addMainValue(int threadIndex, int eventIndex, double value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getMainValue()
	 */
	public double getMainValue(int threadIndex) {
		return 0.0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#getMainEventName()
	 */
	public String getMainEventName() {
		String name = new String("");
		return name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IDatosCluster#addValue()
	 */
	public void addValue(int vectorIndex, int dimensionIndex, String value) {
		Instance i = instances.instance(vectorIndex);
		i.setValue(dimensionIndex, value);
	}
	
	/**
	 * Devuelve las instancias de cluster similares a una dada por parámetro, se basa en la
	 * distancia Euclídea entre las instancias de cluster y la instancia original.
	 * 
	 * @param instsCluster
	 * 			Array de instancia de Weka (Instance)
	 * @param instOrig
	 * 			Instancia origen sobre la que comparar
	 * @param numResult
	 * 			Número de instancias similares a devolver
	 * @return Instance[]
	 * 
	 * */
	public Instance[] getSimiliarInstance(Instance[] instsCluster, Instance instOrig, int numResult) {
		Instance[] instances = new Instance[numResult];
		HashMap<Instance, Double> mapResult = new HashMap<Instance, Double>();
		
		for (int i = 0; i < instsCluster.length; i ++) {
			double dEuclideaAux = getDistanciaEuclidea(instOrig.toDoubleArray(), instsCluster[i].toDoubleArray());
			mapResult.put(instsCluster[i], new Double(dEuclideaAux));
		}
		
		mapResult = WekaUtilities.sortHashIntances(mapResult);
		
		Iterator<Instance> it = mapResult.keySet().iterator();
		int i = 0;
		while(it.hasNext() && i < numResult) {
			instances[i] = it.next();
			i++;
		}
		
		return instances;
	}
	
	/**
	 * Genera instancias de Weka adaptadas al sistema (WekaSRIInstance) similares a una dada por parámetro, se basa en la
	 * distancia Euclídea entre las instancias y la instancia original.
	 * 
	 * @param array de objetos WekaSRIInstance
	 * @param instancia Weka original: Instance
	 * @param número de resultados a devolver
	 * 
	 * @return WekaSRIInstance[]
	 * */
	public WekaSRIInstance[] getSimiliarWekaSRIInstance(WekaSRIInstance[] instsCluster, Instance instOrig, int numResult) {
		WekaSRIInstance[] wekaInstances = new WekaSRIInstance[numResult];
		HashMap<WekaSRIInstance, Double> mapResult = new HashMap<WekaSRIInstance, Double>();
		
		for (int i = 0; i < instsCluster.length; i ++) {
			double dEuclideaAux = getDistanciaEuclidea(instOrig.toDoubleArray(), instsCluster[i].getInstance().toDoubleArray());
			mapResult.put(instsCluster[i], new Double(dEuclideaAux));
		}
		
		mapResult = WekaUtilities.sortHashWekaIntancesV2(mapResult);
		
		Iterator<WekaSRIInstance> it = mapResult.keySet().iterator();
		int i = 0;
		while(it.hasNext() && i < numResult) {
			WekaSRIInstance instance = it.next();
			if (instance != null) {
				wekaInstances[i] = instance;
				i++;
			}
		}
		
		return wekaInstances;
	}
	
	/**
	 * Genera todas las instancias de Weka adaptadas al sistema (WekaSRIInstance) similares a una dada por parámetro, 
	 * se basa en la distancia Euclídea entre las instancias y la instancia original.
	 * 
	 * @param array de objetos WekaSRIInstance
	 * @param instancia Weka original: Instance
	 * 
	 * @return WekaSRIInstance[]
	 * */
   public WekaSRIInstance[] getSimiliarWekaSRIInstance(WekaSRIInstance[] instsCluster, Instance instOrig) {
        WekaSRIInstance[] wekaInstances;
        HashMap<WekaSRIInstance, Double> mapResult = new HashMap<WekaSRIInstance, Double>();
        
        for (int i = 0; i < instsCluster.length; i ++) {
            double dEuclideaAux = getDistanciaEuclidea(instOrig.toDoubleArray(), instsCluster[i].getInstance().toDoubleArray());
            mapResult.put(instsCluster[i], new Double(dEuclideaAux));
        }
        
        mapResult = WekaUtilities.sortHashWekaIntancesV2(mapResult);
        
        Iterator<WekaSRIInstance> it = mapResult.keySet().iterator();
        wekaInstances = new WekaSRIInstance[mapResult.size()];
        int i = 0;
        while(it.hasNext() && i < mapResult.size()) {
            WekaSRIInstance instance = it.next();
            if (instance != null) {
                wekaInstances[i] = instance;
                i++;
            }
        }
        return wekaInstances;
    }

}
