package es.uclm.sri.clustering;

/**
 * Intefaz para definir las funciones básicas del algoritmo Simple K Means
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public interface ISimpleKMeansCluster {

	public void doPCA(boolean doPCA);

	/**
	 * Set valor K (número de clusters)
	 * 
	 * @param k
	 */
	public void setK(int k);

	/**
	 * Get valor K (número de clusters)
	 * 
	 * @return
	 */
	public int getK();

}
