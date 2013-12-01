package es.uclm.sri.clustering.weka;

import java.util.ArrayList;
import java.util.List;

import es.uclm.sri.clustering.IDatosCluster;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.SelectedTag;

public class WekaRegresionLineal extends LinearRegression {

	private LinearRegression regress;
	private Instances instances;

	/**
	 * 
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
			// System.out.println(regress.toString());
		} catch (Exception e) {
			System.err.println("Error performing linear regression");
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
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
		// System.out.println(this.instances.toString());
		this.instances.setClassIndex(instances.numAttributes() - 1);
	}

}
