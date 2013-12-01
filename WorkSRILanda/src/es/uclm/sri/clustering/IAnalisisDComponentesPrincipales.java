package es.uclm.sri.clustering;

/**
 * Interface para definir las funciones del ACP (o ACP en inglés)
 * 
 * @author sernav
 * @version 0.1
 * @since 0.1
 * */
public interface IAnalisisDComponentesPrincipales {

	/**
	 * This method performs the Principal Components Analysis
	 * 
	 * @throws ClusterException
	 */
	public void doPCA() throws ClusterException;

	/**
	 * This method gets the ith Principal Comonent object
	 * 
	 * @param i
	 * @return a ClusterDescription
	 * @throws ClusterException
	 */
	public ClusterDescripcion getComponentDescription(int i)
			throws ClusterException;

	/**
	 * Sets the input data for the clustering operation.
	 * 
	 * @param inputData
	 */
	public void setInputData(IDatosCluster inputData);

	/**
	 * Returns the results of the PCA analysis.
	 * 
	 * @return
	 */
	public IDatosCluster getResults();

	/**
	 * Specifies the clusterer to use if you wish to perform PCA after the
	 * clustering has been done. This is used for linear projection of the
	 * results.
	 * 
	 * @param clusterer
	 */
	public void setClusterer(ICluster clusterer);

	/**
	 * Used to return the PCA reduced data.
	 * 
	 * @return
	 */
	public IDatosCluster[] getClusters();

	/**
	 * Used to reset the PCA. If the user wishes to rerun the analysis with
	 * different parameters, they don't have to create a new PCA class to do it.
	 */
	public void reset();

	/**
	 * Sets the maximum number of components returned.
	 * 
	 * @param maxComponents
	 */
	public void setMaxComponents(int maxComponents);

}
