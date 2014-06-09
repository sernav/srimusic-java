package es.uclm.sri.clustering;

/**
 * Interfaz para definir una jerarquía de clases de clustering
 * 
 * @author Sergio Navarro
 * @version 0.1
 * */
public interface ICluster {

	/**
	 * Ejecuta el clustering con el método k-means
	 * 
	 * @throws ClusterException
	 */
	public void buscarClusters() throws ClusterException;

	/**
	 * Devuelve el objeto ClusterDescription del cluster
	 * 
	 * @param i: int
	 * @return obj ClusterDescription
	 * @throws ClusterException
	 */
	public ClusterDescripcion getClusterDescription(int i)
			throws ClusterException;

	/**
	 * Establece los datos en entrada para el clustering
	 * 
	 * @param input
	 */
	public void setInputData(IDatosCluster input);

	/**
	 * Devuelve los centroides del cluster
	 * 
	 * @return IDatosCluster
	 */
	public IDatosCluster getClusterCentroids();

	/**
	 * Devuelve el cluster con valores menores
	 * 
	 * @return IDatosCluster
	 */
	public IDatosCluster getClusterMinimums();

	/**
	 * Devuelve el cluster con valores máximos
	 * 
	 * @return IDatosCluster
	 */
	public IDatosCluster getClusterMaximums();

	/**
	 * Obtiene la desviación estandar del clustering
	 * 
	 * @return IDatosCluster
	 */
	public IDatosCluster getClusterStandardDeviations();

	/**
	 * Reinicia el clustering.
	 * Si necesitamos cambiar los parámetros del clustering
	 */
	public void reset();

	/**
	 * Método para obtener el números de elementos de cada cluster
	 * 
	 * @return int[]
	 */
	public int[] getClusterSizes();

	/**
	 * Método para obtener el identificador de los clusters
	 * 
	 * @return int[]
	 */
	public int[] clusterInstances();

	/**
	 * Método que devuelve los identificadores de cluster que contienen el 
	 * individuo "i" del conjunto de individuos.
	 * 
	 * @param i: int
	 * @return int
	 */
	public int clusterInstance(int i);

	/**
	 * Número de instancias en el clustering
	 * 
	 * @return int
	 */
	public int getNumInstances();

}
