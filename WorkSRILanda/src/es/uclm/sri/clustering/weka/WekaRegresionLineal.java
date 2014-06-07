package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.List;

import es.uclm.sri.clustering.IDatosCluster;
import es.uclm.sri.sis.log.Log;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SelectedTag;

/**
 * Determinar la relación entre instancias Weka mediante Regresión Lineal
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class WekaRegresionLineal extends LinearRegression {

	private LinearRegression regress;
	private Instances instances;

	/**
	 * Constructor
	 */
	public WekaRegresionLineal() {
		regress = new LinearRegression();
		regress.setEliminateColinearAttributes(false);
		regress.setAttributeSelectionMethod(new SelectedTag(
				LinearRegression.SELECTION_NONE,
				LinearRegression.TAGS_SELECTION));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.LinearRegressionInterface#findCoefficients()
	 */
	public void findCoefficients() {
		try {
			regress.buildClassifier(instances);
		} catch (Exception e) {
			Log.log(e, "Error al generar la regresión lineal!" + e.getMessage());
			System.exit(0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clustering.LinearRegressionInterface#getCoefficients()
	 */
	public List<Double> getCoefficients() {
		List<Double> coefficients = new ArrayList<Double>();
		double[] params = regress.coefficients();
		for (int i = 0; i < params.length; i++) {
			coefficients.add(new Double(params[i]));
		}
		return coefficients;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * clustering.LinearRegressionInterface#setInputData(clustering.RawDataInterface
	 * )
	 */
	public void setInputData(IDatosCluster inputData) {
		this.instances = (Instances) inputData.getData();
		this.instances.setClassIndex(instances.numAttributes() - 1);
	}

}
