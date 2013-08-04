package es.uclm.sri.clustering;

/**
 * Interfaz para definir una jerarqu’a de clases de clustering
 * 
 * @author sernav
 * @version 0.1
 * @since 0.1
 * 
 * */
public interface ICluster {

	/**
	 * This method performs the K means clustering
	 * 
	 * @throws ClusterException
	 */
	public void buscarClusters() throws ClusterException;

	/**
	 * This method gets the ith ClusterDescription object
	 * 
	 * @param i
	 * @return a ClusterDescription
	 * @throws ClusterException
	 */
	public ClusterDescripcion getClusterDescription(int i)
			throws ClusterException;

	/**
	 * Sets the input data for the clustering operation.
	 * 
	 * @param inputData
	 */

	public void setInputData(IDatosCluster input);

	/**
	 * Method to get the cluster centroids (averages).
	 * 
	 * @return
	 */
	public IDatosCluster getClusterCentroids();

	/**
	 * Method to get the cluster minimum values.
	 * 
	 * @return
	 */
	public IDatosCluster getClusterMinimums();

	/**
	 * Method to get the cluster maximum values.
	 * 
	 * @return
	 */
	public IDatosCluster getClusterMaximums();

	/**
	 * Method to get the cluster standard deviation values.
	 * 
	 * @return
	 */
	public IDatosCluster getClusterStandardDeviations();

	/**
	 * Reset method, for resetting the cluster. If a user loads this object with
	 * data, and then does several clusterings with several K values, then we
	 * need a reset method.
	 * 
	 */
	public void reset();

	/**
	 * Method to get the number of individuals in each cluster.
	 * 
	 * @return
	 */
	public int[] getClusterSizes();

	/**
	 * Method to get the cluster ID for the cluster that contains individual
	 * "i".
	 * 
	 * @param i
	 * @return
	 */

	public int[] clusterInstances();

	/**
	 * Method to get the cluster IDs for the cluster that contains each
	 * individual "i" in the set of individuals.
	 * 
	 * @param i
	 * @return
	 */
	public int clusterInstance(int i);

	/**
	 * Get the number of individuals that we are clustering.
	 * 
	 * @return
	 */
	public int getNumInstances();

}
