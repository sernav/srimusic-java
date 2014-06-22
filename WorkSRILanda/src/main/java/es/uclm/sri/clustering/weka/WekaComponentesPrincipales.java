package es.uclm.sri.clustering.weka;

import java.util.ArrayList;

import weka.attributeSelection.PrincipalComponents;
import weka.core.Instance;
import weka.core.Instances;
import es.uclm.sri.clustering.ClusterDescripcion;
import es.uclm.sri.clustering.ClusterException;
import es.uclm.sri.clustering.IAnalisisDComponentesPrincipales;
import es.uclm.sri.clustering.ICluster;
import es.uclm.sri.clustering.IDatosCluster;
import es.uclm.sri.sis.log.Log;

/**
 * Análisis de componentes principales (PCA) en Weka.
 * Implementa la inteface IAnalisisDComponentesPrincipales
 * 
 * @author Sergio Navarro
 * @version 1.0
 */
public class WekaComponentesPrincipales implements
		IAnalisisDComponentesPrincipales {

	// Atributos de descripción del cluster
	private IDatosCluster inputData = null;
	private Instances instances = null;
	private Instances components = null;
	private PrincipalComponents pca = null;
	private int numAttributes = 0;
	private double[][] correlationCoefficients = null;
	private IDatosCluster rawData = null;
	private ICluster clusterer = null;
	private IDatosCluster[] clusters = null;
	private IDatosCluster transformed = null;
	private int maxComponents = 2;

	/**
	 * Constructor protegido
	 * 
	 * @param rawData
	 * 			IDatosCluster
	 */
	WekaComponentesPrincipales(IDatosCluster rawData) {
		this.rawData = rawData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IAnalisisDComponentesPrincipales#doPCA()
	 */
	public void doPCA() throws ClusterException {

		if (this.rawData != null) {
			// Ejecuta el PCA para el dataset ya completo
			try {
				this.pca = new PrincipalComponents();
				if (this.maxComponents > 0)
					pca.setMaximumAttributeNames(this.maxComponents);
				// pca.setNormalize(false);
				pca.setTransformBackToOriginal(false);
				Instances instances = (Instances) rawData.getData();
				pca.buildEvaluator(instances);
				System.out.println("variance covered: "
						+ pca.getVarianceCovered());
				for (int i = 0; i < ((Instances) rawData.getData())
						.numAttributes(); i++) {
					System.out.println("merit[" + i + "]: "
							+ pca.evaluateAttribute(i));
				}
				components = pca.transformedData(instances);
				transformed = new WekaDatosCluster(components);
			} catch (Exception e) {
				Log.log(e, "Error al ejecutar PCA para el dataset");
				e.printStackTrace(System.err);
			}
		} else {
			// Análisis de correlación para dos componentes
			ArrayList<String> names = new ArrayList<String>();
			for (int i = 0; i < maxComponents; i++) {
				names.add(clusters[i].getName()); // <-- OJO
			}

			transformed = AnalysisFactory.createRawData("Scatterplot Data",
					names, 2, inputData.numVectors(), null);
			for (int i = 0; i < inputData.numVectors(); i++) {
				float[] values = null;
				for (int j = 0; j < maxComponents; j++) {
					transformed.addValue(j, i, (double) (values[j]));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * clustering.IAnalisisDComponentesPrincipales#getComponentDescription
	 * (int)
	 */
	public ClusterDescripcion getComponentDescription(int i)
			throws ClusterException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * clustering.IAnalisisDComponentesPrincipales#setInputData(clustering
	 * .IDatosCluster)
	 */
	public void setInputData(IDatosCluster inputData) {
		this.inputData = inputData;
		this.instances = (Instances) inputData.getData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * clustering.IAnalisisDComponentesPrincipales#getCorrelationCoefficients
	 * ()
	 */
	public double[][] getCorrelationCoefficients() {
		if (this.correlationCoefficients == null) {
			this.numAttributes = this.instances.numAttributes();
			this.correlationCoefficients = new double[numAttributes][numAttributes];
			for (int i = 0; i < numAttributes; i++) {
				double[] event1 = instances.attributeToDoubleArray(i);
				for (int j = 0; j < i; j++) {
					double[] event2 = instances.attributeToDoubleArray(j);
					this.correlationCoefficients[i][j] = weka.core.Utils
							.correlation(event1, event2, numAttributes);
				}
			}
		}
		return correlationCoefficients;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IAnalisisDComponentesPrincipales#getResults()
	 */
	public IDatosCluster getResults() {
		return transformed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IAnalisisDComponentesPrincipales#reset()
	 */
	public void reset() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IAnalisisDComponentesPrincipales#getClusters()
	 */
	public IDatosCluster[] getClusters() {
		if (this.clusterer != null) {
			int[] clusterSizes = clusterer.getClusterSizes();
			int k = clusterer.getClusterSizes().length;
			if (k == 0) {
				clusters = null;
				return clusters;
			}

			clusters = new IDatosCluster[k];
			Instances[] instances = new Instances[k];
			int[] counters = new int[k];

			for (int i = 0; i < k; i++) {
				// Comprobación para clusters que Weka los cree vacíos.
				if (i >= clusterSizes.length) {
					instances[i] = new Instances((Instances) (transformed.getData()), 0);
				} else {
					instances[i] = new Instances((Instances) (transformed.getData()),
							clusterSizes[i]);
				}	
				counters[i] = 0;
			}

			// int x = transformed.numDimensions() - 1;
			// int y = transformed.numDimensions() - 2;
			// int x = 0;
			// int y = 1;
			for (int i = 0; i < inputData.numVectors(); i++) {
				double values[] = new double[2];
				int location = clusterer.clusterInstance(i);
				if (location < 0) {
					location = instances.length - 1; // se almacena en el último cluster
				}
				values[0] = transformed.getValue(0, i);
				values[1] = transformed.getValue(1, i);
				instances[location].add(new Instance(1.0, values));
				counters[location]++;
			}

			for (int i = 0; i < k; i++) {
				clusters[i] = new WekaDatosCluster(instances[i]);
			}
		}
		return clusters;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.IAnalisisDComponentesPrincipales#setClusterer()
	 */
	public void setClusterer(ICluster clusterer) {
		this.clusterer = clusterer;
	}

	/**
	 * @return Número máximo de componentes
	 */
	public int getMaxComponents() {
		return maxComponents;
	}

	/**
	 * @param maxComponents
	 *            Número máximo de componenetes para el dataset
	 */
	public void setMaxComponents(int maxComponents) {
		this.maxComponents = maxComponents;
	}

}