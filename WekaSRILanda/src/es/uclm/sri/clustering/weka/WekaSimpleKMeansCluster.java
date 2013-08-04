package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.Enumeration;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import es.uclm.sri.clustering.ClusterException;
import es.uclm.sri.clustering.ISimpleKMeansCluster;

/**
 * Operaci—n de clustering. Lista de nombre y valores que definen los grupos
 * (clusters)
 * 
 * @author sernav
 * @version 1.0
 * @since 1.0
 * 
 */

public class WekaSimpleKMeansCluster extends WekaAbstractCluster implements
		ISimpleKMeansCluster {

	// the number of clusters to find
	private int k = 0;

	private SimpleKMeans kmeans = null;

	/**
	 * Default constructor - package protected
	 */
	WekaSimpleKMeansCluster() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansCluster#findClusters()
	 */
	public void buscarClusters() throws ClusterException {
		// assert instances != null : instances;
		try {
			this.kmeans = new SimpleKMeans();
			kmeans.setNumClusters(k);
			Instances localInstances = null;

			if (this.doPCA) {
				localInstances = handlePCA(instances);
			} else
				localInstances = this.instances;

			kmeans.buildClusterer(localInstances);
			this.clusterCentroids = kmeans.getClusterCentroids();
			this.clusterStandardDeviations = kmeans.getClusterStandardDevs();
			evaluateCluster();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansCluster#setK(int)
	 */
	public void setK(int k) {
		this.k = k;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansCluster#setK(int)
	 */
	public void setSimpleKMeans() throws Exception {
		this.kmeans = new SimpleKMeans();
		kmeans.setNumClusters(this.k);
	}

	public void setSimpleKMeans(SimpleKMeans kmeans) throws Exception {
		this.kmeans = kmeans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansCluster#getK()
	 */
	public int getK() {
		return this.k;
	}

	public int[] getClusterSizes() {
		return this.kmeans.getClusterSizes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.KMeansClusterInterface#clusterInstance(int)
	 */
	public int clusterInstance(int i) {
		// assert kmeans != null : kmeans;
		int retval = 0;
		try {
			retval = kmeans.clusterInstance(instances.instance(i));
		} catch (Exception e) {
		}
		return retval;
	}

	protected double getSquaredError() {
		return kmeans.getSquaredError();
	}

	public int clusterInstance(Instance instance) throws Exception {
		return kmeans.clusterInstance(instance);
	}

	public Instance[] getInstancesDCluster(Enumeration<Instance> enumInst,
			int numCluster) throws Exception {
		ArrayList<Instance> arrayCluster = new ArrayList<Instance>();
		while (enumInst.hasMoreElements()) {
			Instance instance = enumInst.nextElement();
			if (kmeans.clusterInstance(instance) == numCluster) {
				arrayCluster.add(instance);
			}
		}
		
		return arrayCluster.toArray(new Instance[arrayCluster.size()]);
	}

}
