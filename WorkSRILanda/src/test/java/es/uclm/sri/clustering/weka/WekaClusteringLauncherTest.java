package test.java.es.uclm.sri.clustering.weka;

import static org.junit.Assert.assertNotNull;
import main.java.es.uclm.sri.clustering.weka.AnalysisFactory;
import main.java.es.uclm.sri.clustering.weka.WekaClusteringFacade;
import main.java.es.uclm.sri.clustering.weka.WekaDatosCluster;
import main.java.es.uclm.sri.clustering.weka.WekaSRIInstance;
import main.java.es.uclm.sri.clustering.weka.WekaSimpleKMeansCluster;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClusteringLauncherTest {

	private static Instances data;
	private static SimpleKMeans clusterer;
	private static WekaSimpleKMeansCluster wekaKMeans;

	private double[] attValues;

	@Before
	public void setUp() throws Exception {
		attValues = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	}

	@After
	public void tearDown() throws Exception {
		attValues = null;
		clusterer = null;
		wekaKMeans = null;
	}

	@Test
	public void testWekaClusteringLauncher() {
		WekaClusteringFacade launcher = new WekaClusteringFacade();
		assertNotNull("Objeto launcher generado", launcher);
		
		data = launcher.getData();
		clusterer = launcher.getClusterer();
		assertNotNull("Dataset generado", data);
		
		WekaSRIInstance inst = new WekaSRIInstance(1.0, attValues);
		AnalysisFactory.buildFactory();

		WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
				.createRawData(data);

		int iCluster = -99;
		try {
			wekaKMeans = new WekaSimpleKMeansCluster();
			wekaKMeans.setInputData(wekaDatosCluster);
			wekaKMeans.setK(clusterer.numberOfClusters());
			wekaKMeans.setSimpleKMeans(clusterer);
			
			assertNotNull("Proceso de clustering Weka finalizado con éxito", wekaKMeans);
			
			iCluster = wekaKMeans.clusterInstance(inst);
			Instance centroide = clusterer.getClusterCentroids().instance(iCluster);

			double dEuclidea = wekaDatosCluster.getDistanciaEuclideaCentroide(
					centroide.toDoubleArray(), attValues);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
