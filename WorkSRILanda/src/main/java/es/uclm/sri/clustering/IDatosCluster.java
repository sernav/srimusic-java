package main.java.es.uclm.sri.clustering;

import java.util.List;

/**
 * Interfaz que define los métodos para manejar los datos de clusters.
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public interface IDatosCluster {

	/**
	 * Añade valor al objeto en la posición determinada
	 * 
	 * @param índice del vector
	 * @param dimensión del índice
	 * @param value
	 */
	public void addValue(int vectorIndex, int dimensionIndex, double value);

	/**
	 * Devuelve el valor del objeto especificado por los índices del parámetro
	 * 
	 * @param índice del vector
	 * @param dimensión del índice
	 * @return double
	 */
	public double getValue(int vectorIndex, int dimensionIndex);

	/**
	 * Devuelve la distancia entre dos puntos del vector calculada con el algorítmo
	 * de distancia Manhattan
	 * 
	 * @param Primer elemento del vector
	 * @param Segundo elemento del vector
	 * @return double
	 */
	public double getManhattanDistance(int firstVector, int secondVector);

	/**
	 * Devuelve la distancia entre dos puntos del vector calculada con el algorítmo
	 * de distancia Cartesiana
	 * 
	 * @param Primer elemento del vector
	 * @param Segundo elemento del vector
	 * @return double
	 */
	public double getCartesianDistance(int firstVector, int secondVector);

	/**
	 * Devuelve el objeto que almacena los datos del cluster.
	 * 
	 * @return Object
	 */
	public Object getData();

	/**
	 * Devuelve una lista de nombres del data set.
	 * 
	 * @return List<String>
	 */
	public List<String> getEventNames();

	/**
	 * Devuelve el numero de vectores en el data set.
	 * 
	 * @return int
	 */
	public int numVectors();

	/**
	 * Devuelve el número de dimensiones en el data set.
	 * 
	 * @return int
	 */
	public int numDimensions();

	/**
	 * Devuelve el nombre del data set.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Devuelve el valor máximo para el data set.
	 * 
	 * @return double
	 */
	public double getMaximum();

	/**
	 * Devuelve el valor de datos del índice
	 * 
	 * @param índice
	 * @return double[]
	 */
	public double[] getVector(int i);

	/**
	 * Devuelve el coeficiente de correlación entre los elementos x e y del vector.
	 * 
	 * @param x
	 * @param y
	 * @return double
	 */
	public double getCorrelation(int x, int y);

	/**
	 * Añade el valor a la dimensión "eventIndex" del vector "threadIndex".
	 * 
	 * @param threadIndex
	 * @param eventIndex
	 * @param value
	 */
	public void addMainValue(int threadIndex, int eventIndex, double value);

	/**
	 * Devuelve el valor indicado por el índice
	 * 
	 * @param threadIndex
	 * @return
	 */
	public double getMainValue(int threadIndex);

	/**
	 * Devuelve el nombre de la función principal.
	 * 
	 * @return
	 */
	public String getMainEventName();

	/**
	 * Establece un atributo en la instancia cuando el atributo es de tipo cadena
	 * 
	 * @param vectorIndex
	 * @param dimensionIndex
	 * @param value
	 */
	public void addValue(int vectorIndex, int dimensionIndex, String value);

}
