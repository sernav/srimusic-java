package es.uclm.sri.clustering.weka;

import weka.attributeSelection.PrincipalComponents;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import es.uclm.sri.clustering.ClusterDescripcion;
import es.uclm.sri.clustering.ClusterException;
import es.uclm.sri.clustering.ICluster;
import es.uclm.sri.clustering.IDatosCluster;

/**
 * Módulo clustering:
 * 		Descripción y valores de un cluster.
 * 
 * @author Sergio Navarro
 * @version 1.0
 */
public abstract class WekaAbstractCluster implements ICluster {

	protected boolean doPCA = false;

	protected Instances instances = null;
	protected Instances clusterCentroids = null;
	protected Instances clusterMaximums = null;
	protected Instances clusterMinimums = null;
	protected Instances clusterStandardDeviations = null;

	protected IDatosCluster inputData = null;

	/**
	 * Constructor por defecto
	 */
	WekaAbstractCluster() {
		super();
		reset();
	}

	/**
	 * Reseteo completo del cluster.
	 * Debe hacerse cuando se carga el objeto con distintos valores de K.
	 */
	public void reset() {
		this.clusterCentroids = null;
		this.clusterMaximums = null;
		this.clusterMinimums = null;
		this.clusterStandardDeviations = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansClusterInterface#setInputData(IDatosCluster)
	 */
	public void setInputData(IDatosCluster inputData) {
		this.instances = (Instances) inputData.getData();
		this.inputData = inputData;
	}

	public Instances handlePCA(Instances instances) throws Exception {
		PrincipalComponents pca = new PrincipalComponents();
		pca.setMaximumAttributeNames(1);
//		pca.setNormalize(true);
		pca.setTransformBackToOriginal(true);
		pca.buildEvaluator(instances);
		return pca.transformedData(instances);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getClusterDescription(int)
	 */
	public ClusterDescripcion getClusterDescription(int i)
			throws ClusterException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getClusterCentroids()
	 */
	public IDatosCluster getClusterCentroids() {
		WekaDatosCluster centroids = new WekaDatosCluster(clusterCentroids);
		return centroids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getClusterStandardDeviations()
	 */
	public IDatosCluster getClusterStandardDeviations() {
		if (clusterStandardDeviations == null)
			return null;
		WekaDatosCluster deviations = new WekaDatosCluster(clusterStandardDeviations);
		return deviations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ISimpleKMeansCluster#doPCA(boolean)
	 */
	public void doPCA(boolean doPCA) {
		this.doPCA = doPCA;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getNumInstances()
	 */
	public int getNumInstances() {
		return instances.numInstances();
	}

	protected abstract double getSquaredError();

	protected abstract void evaluateCluster() ;

	public abstract int clusterInstance(int i);

	public abstract int clusterInstance(Instance instance) throws Exception;

	public int[] clusterInstances() {
		int[] clusterIDs = new int[instances.numInstances()];
		for (int i = 0; i < instances.numInstances(); i++)
			clusterIDs[i] = clusterInstance(i);
		return clusterIDs;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getClusterMaximums()
	 */
	public IDatosCluster getClusterMaximums() {
		if (this.clusterMaximums == null) {
			try {
				Instances instances = (Instances) this.inputData.getData();
				this.clusterMaximums = new Instances(instances, 0);
				int nc = clusterCentroids.numInstances();
				Instances[] temp = new Instances[nc];
				for (int i = 0; i < nc; i++) {
					temp[i] = new Instances(instances, 0);
				}
				for (int i = 0; i < instances.numInstances(); i++) {
					int cNum = clusterInstance(instances.instance(i));
					if (cNum >= 0) {
						temp[cNum].add(new Instance(1.0, instances.instance(i)
								.toDoubleArray()));
					}
				}
				// iterate over the clusters
				for (int i = 0; i < nc; i++) {
					double[] vals = new double[instances.numAttributes()];
					for (int dimension = 1; dimension < temp[i].numAttributes(); dimension++) {
						vals[dimension] = temp[i].instance(0).value(dimension);
					}
					// iterate over the dimensions
					for (int dimension = 1; dimension < temp[i].numAttributes(); dimension++) {
						for (int vector = 0; vector < temp[i].numInstances(); vector++) {
							vals[dimension] = Math.max(vals[dimension], temp[i]
									.instance(0).value(dimension));
						}
					}
					// assign the new centroid value
					this.clusterMaximums.add(new Instance(1.0, vals));
				}
			} catch (Exception e) {
				System.err.println("getServer exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		WekaDatosCluster maximums = new WekaDatosCluster(clusterMaximums);
		return maximums;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.ICluster#getClusterMinimums()
	 */
	public IDatosCluster getClusterMinimums() {
		if (this.clusterMinimums == null) {
			try {
				Instances instances = (Instances) this.inputData.getData();
				this.clusterMinimums = new Instances(instances, 0);
				int nc = clusterCentroids.numInstances();
				Instances[] temp = new Instances[nc];
				for (int i = 0; i < nc; i++) {
					temp[i] = new Instances(instances, 0);
				}
				for (int i = 0; i < instances.numInstances(); i++) {
					int cNum = clusterInstance(instances.instance(i));
					if (cNum >= 0) {
						temp[clusterInstance(instances.instance(i))]
								.add(new Instance(1.0, instances.instance(i)
										.toDoubleArray()));
					}
				}
				// iterate over the clusters
				for (int i = 0; i < nc; i++) {
					double[] vals = new double[instances.numAttributes()];

					for (int dimension = 1; dimension < temp[i].numAttributes(); dimension++) {
						vals[dimension] = temp[i].instance(0).value(dimension);
					}
					// iterate over the dimensions
					for (int dimension = 1; dimension < temp[i].numAttributes(); dimension++) {
						for (int vector = 0; vector < temp[i].numInstances(); vector++) {
							vals[dimension] = Math.min(vals[dimension], temp[i]
									.instance(0).value(dimension));
						}
					}
					// assign the new centroid value
					this.clusterMinimums.add(new Instance(1.0, vals));
				}
			} catch (Exception e) {
				System.err.println("getServer exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		WekaDatosCluster minimums = new WekaDatosCluster(clusterMinimums);
		return minimums;
	}

	public double getBetweenError() {
		// get the mean of the centroids
		Instance centroidMean = new Instance(
				this.clusterCentroids.numAttributes());
		for (int x = 0; x < this.clusterCentroids.numInstances(); x++) {
			Instance tmpInst = this.clusterCentroids.instance(x);
			for (int y = 0; y < tmpInst.numAttributes(); y++) {
				double tmp = centroidMean.value(y) + tmpInst.value(y);
				centroidMean.setValue(y, tmp);
			}
		}
		// get the squared error for the centroids
		double betweenError = 0.0;
		for (int x = 0; x < this.clusterCentroids.numInstances(); x++) {
			betweenError += distance(centroidMean,
					this.clusterCentroids.instance(x));
		}
		return betweenError;
	}

	/**
	 * Calculates the distance between two instances
	 * 
	 * @param test
	 *            the first instance
	 * @param train
	 *            the second instance
	 * @return the distance between the two given instances, between 0 and 1
	 */
	private double distance(Instance first, Instance second) {

		double distance = 0;
		int firstI, secondI;

		for (int p1 = 0, p2 = 0; p1 < first.numValues()
				|| p2 < second.numValues();) {
			if (p1 >= first.numValues()) {
				firstI = this.clusterCentroids.numAttributes();
			} else {
				firstI = first.index(p1);
			}
			if (p2 >= second.numValues()) {
				secondI = this.clusterCentroids.numAttributes();
			} else {
				secondI = second.index(p2);
			}
			if (firstI == this.clusterCentroids.classIndex()) {
				p1++;
				continue;
			}
			if (secondI == this.clusterCentroids.classIndex()) {
				p2++;
				continue;
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

	/**
	 * Computes the difference between two given attribute values.
	 */
	private double difference(int index, double val1, double val2) {

		switch (this.clusterCentroids.attribute(index).type()) {
		case Attribute.NOMINAL:

			// If attribute is nominal
			if (Instance.isMissingValue(val1) || Instance.isMissingValue(val2)
					|| ((int) val1 != (int) val2)) {
				return 1;
			} else {
				return 0;
			}
		case Attribute.NUMERIC:

			// If attribute is numeric
			if (Instance.isMissingValue(val1) || Instance.isMissingValue(val2)) {
				if (Instance.isMissingValue(val1)
						&& Instance.isMissingValue(val2)) {
					return 1;
				} else {
					double diff;
					if (Instance.isMissingValue(val2)) {
						diff = val1;
					} else {
						diff = val2;
					}
					if (diff < 0.5) {
						diff = 1.0 - diff;
					}
					return diff;
				}
			} else {
				return val1 - val2;
			}
		default:
			return 0;
		}
	}

}