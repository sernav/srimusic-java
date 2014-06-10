package main.java.es.uclm.sri.clustering;

/**
 * Interface para definir las funciones del PCA
 * 
 * @author Sergio Navarro
 * @version 0.1
 * */
public interface IAnalisisDComponentesPrincipales {

	/**
	 * Método para el análisis de componentes principales
	 * 
	 * @throws ClusterException
	 */
	public void doPCA() throws ClusterException;

	/**
	 * Obtiene el componente principal
	 * 
	 * @param i: int
	 * @return obj ClusterDescription
	 * @throws ClusterException
	 */
	public ClusterDescripcion getComponentDescription(int i)
			throws ClusterException;

	/**
	 * Establece los datos de entrada para el clustering
	 * 
	 * @param inputData: IDatosCluster
	 */
	public void setInputData(IDatosCluster inputData);

	/**
	 * Devuelve los resultados del análisis
	 * 
	 * @return IDatosCluster
	 */
	public IDatosCluster getResults();

	/**
	 * Especifica el clusterer a utilizar para realizar el PCA
	 * 
	 * @param clusterer: ICluster
	 */
	public void setClusterer(ICluster clusterer);

	/**
	 * Devuelve los clusterer del PCA
	 * 
	 * @return IDatosCluster[]
	 */
	public IDatosCluster[] getClusters();

	/**
	 * Restablece el PCA.
	 * Ejecutar antes de un nuevo análisis con difrentes parámetros.
	 */
	public void reset();

	/**
	 * Establece el número máximo de componentes del PCA.
	 * 
	 * @param maxComponents: int
	 */
	public void setMaxComponents(int maxComponents);

}
