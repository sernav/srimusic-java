package es.uclm.sri.clustering.weka;

import java.util.List;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import es.uclm.sri.clustering.IAnalisisDComponentesPrincipales;
import es.uclm.sri.clustering.IDatosCluster;
import es.uclm.sri.clustering.ISimpleKMeansCluster;

public class AnalysisFactory {

	/**
	 * Method to construct the specified factory. This method is used to
	 * construct the specified factory.
	 * 
	 * @param factoryType
	 *            the type of factory to create
	 * @return an AnalysisFactory
	 * 
	 */
	public static AnalysisFactory buildFactory() {
		return new AnalysisFactory();
	};

	/**
	 * Method to create a IDatosCluster object. Any extention of the
	 * AnalysisFactory class has to implement this method.
	 * 
	 * @param name
	 *            The description of the data
	 * @param attributes
	 *            The list of column names
	 * @param vectors
	 *            The number of rows in the data (for initialization)
	 * @param dimensions
	 *            The number of dimensions to be stored (for initialization)
	 * @param classAttributes
	 *            TODO
	 * @return
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
	 * Method to create the KMeansClusterInterface. Any extention of the
	 * AnalysisFactory class has to implement this method.
	 * 
	 * @return
	 */
	public static ISimpleKMeansCluster createKMeansEngine() {
		return new WekaSimpleKMeansCluster();
	}

	/**
	 * Method to create the KMeansClusterInterface. Any extention of the
	 * AnalysisFactory class has to implement this method.
	 * 
	 * @return
	 */
	public static LinearRegression createLinearRegressionEngine() {
		return new WekaRegresionLineal();
	}

	/**
	 * Method to create a component to perform PCA analysis on the data.
	 * 
	 * @param IDatosCluster
	 * @return
	 */
	public static IAnalisisDComponentesPrincipales createPCAEngine(
			IDatosCluster rawData) {
		return new WekaComponentesPrincipales(rawData);
	}

	/**
	 * Method for shutting down analysis engines, if necessary.
	 */
	public void closeFactory() {
		// do nothing
		return;
	}

	// /**
	// * Method to create a component to normalize the data.
	// *
	// * @param inputData
	// * @return
	// */
	// public static DataNormalizer createDataNormalizer(IDatosCluster
	// inputData) {
	// return new WekaDataNormalizer(inputData);
	// }

	// /**
	// * Method for building Naive Bayes classifier
	// *
	// * @param inputData
	// * @return
	// */
	// public static ClassifierInterface createNaiveBayesClassifier(
	// IDatosCluster inputData) {
	// return new WekaNaiveBayesClassifier(inputData);
	// }

	// /**
	// * Method for building Support Vector Machine classifier
	// *
	// * @param inputData
	// * @return
	// */
	// public static Classifier createSupportVectorClassifier(
	// IDatosCluster inputData) {
	// return new WekaVectorClasificacion(inputData);
	// }
	//
	// public static HierarchicalCluster createHierarchicalClusteringEngine() {
	// return new JavaHierarchicalCluster();
	// }

}
