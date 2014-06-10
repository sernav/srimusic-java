package main.java.es.uclm.sri.clustering.weka;

import java.util.List;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import main.java.es.uclm.sri.clustering.IAnalisisDComponentesPrincipales;
import main.java.es.uclm.sri.clustering.IDatosCluster;
import main.java.es.uclm.sri.clustering.ISimpleKMeansCluster;

/**
 * Módulo clustering:
 * 		Fábrica de análisis de datos de clustering
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class AnalysisFactory {

	/**
	 * Método constructor específico de la fábrica.
	 * 
	 * @return AnalysisFactory
	 */
	public static AnalysisFactory buildFactory() {
		return new AnalysisFactory();
	};

	/**
	 * Genera un objeto que implementa la interface IDatosCluster.
	 * Toda extensión de la fábrica debe implementar este método.
	 * 
	 * @param name
	 * 			Descripción del dato: String
	 * @param attributes
	 * 			Lista de nombres de columna: List
	 * @param vectors
	 * 			Número de filas de datos: int
	 * @param dimensions
	 * 			Dimensiones para ser almacenado
	 * @param classAttributes
	 * 
	 * @return IDatosCluster
	 */
	public static IDatosCluster createRawData(String name,
			List<String> attributes, int vectors, int dimensions,
			List<String> classAttributes) {
		return new WekaDatosCluster(name, attributes, vectors, dimensions,
				classAttributes);
	}
	
	public static IDatosCluster createRawData(Instances instances) {
		return new WekaDatosCluster(instances);
	}

	/**
	 * Método para generar un objeto que implementa la interface ISimpleKMeansCluster.
	 * Cualquier extensión de esta clase debe implementar esta función.
	 * 
	 * @return ISimpleKMeansCluster
	 */
	public static ISimpleKMeansCluster createKMeansEngine() {
		return new WekaSimpleKMeansCluster();
	}

	/**
	 * Método que crea el motor de regresión lineal.
	 * (La función de regresión lineal mide el grado de asociación entre dos variables)
	 * Cualquier extensión de esta clase debe implementar esta función.
	 * 
	 * @return LinearRegression
	 */
	public static LinearRegression createLinearRegressionEngine() {
		return new WekaRegresionLineal();
	}

	/**
	 * Método que crea el componente para realizar el análisis del PCA
	 * 
	 * @param interface datos de cluster
	 * @return IAnalisisDComponentesPrincipales
	 */
	public static IAnalisisDComponentesPrincipales createPCAEngine(
			IDatosCluster rawData) {
		return new WekaComponentesPrincipales(rawData);
	}

	/**
	 * Método para finalizar el análisis.
	 */
	public void closeFactory() {
		return;
	}

}
