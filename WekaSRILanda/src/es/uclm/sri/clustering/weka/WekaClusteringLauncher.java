package es.uclm.sri.clustering.weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class WekaClusteringLauncher {

	private static Instances data = null;
	private static SimpleKMeans clusterer = null;
	private static WekaSimpleKMeansCluster wekaKMeans = null;

	public WekaClusteringLauncher() throws Exception {
		new WekaClusteringLauncher(null);
	}

	public WekaClusteringLauncher(String pathFileARFF) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(pathFileARFF));
		this.data = new Instances(reader);

		reader.close();

		executeWekaKMeans(data);
	}

	public void WekaClusteringModelLauncher(String pathModelWeka)
			throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(pathModelWeka));
		this.data = loader.getStructure();

		data.deleteStringAttributes();

		executeWekaKMeans(data);
	}

	protected void executeWekaKMeans(Instances data) throws Exception {
		String[] options = new String[2];
		options[0] = "-I"; // max. iterations
		options[1] = "500";

		// Algoritmo SimpleKMeans de Weka
		clusterer = new SimpleKMeans();
		clusterer.setOptions(options);
		clusterer.setNumClusters(8);
		clusterer.setSeed(10);
		
		/*
		 * Filtro para los atributos de çlbum y Artista
		 * 
		 * */
		int[] attributes = {0,1};
		Remove rm = new Remove();
		
		rm.setAttributeIndicesArray(attributes);
		rm.setInputFormat(data);
		data = Filter.useFilter(data, rm);

		// Funci—n de c‡lculo de distancias: Euclidea
		EuclideanDistance df = new EuclideanDistance(data);
		df.setAttributeIndices("first-last");
		df.setDontNormalize(false);
		df.setInvertSelection(false);

		clusterer.setDistanceFunction(df);

		// Construcci—n del clusterer con las instancias traidas
		clusterer.buildClusterer(data);

	}

	protected void generarWekaSimpleKMeans(Instances data) {
		WekaDatosCluster wekaDatosCluster = new WekaDatosCluster(data);
		wekaKMeans = new WekaSimpleKMeansCluster();
		wekaKMeans.setInputData(wekaDatosCluster);
	}

	protected int clasificarInstanciaWeka(WekaSRIInstance wekaInst)
			throws Exception {
		int cluster = wekaKMeans.clusterInstance(wekaInst.getInstance());

		return cluster;
	}

	protected String evaluadorClusterer() {
		ClusterEvaluation eval = new ClusterEvaluation();
		if (this.clusterer != null)
			eval.setClusterer(this.clusterer);

		return eval.clusterResultsToString();
	}

	public static void main(String[] args) throws Exception {
		WekaClusteringLauncher launcher = null;
		if (args.length != 1) {
			launcher = new WekaClusteringLauncher(
					"/Users/sergionavarro/PFC/PesosAlbums_Titulados.arff");
		} else {
			launcher = new WekaClusteringLauncher(args[0]);
		}

		// launcher.generarWekaSimpleKMeans(data);

		double[] attValues = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.666667, 0.333333, 0.0, 0.0, 0.0, 0.0 };

		WekaSRIInstance inst = new WekaSRIInstance(1.0, attValues);

		AnalysisFactory.buildFactory();

		WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
				.createRawData(data);
		wekaKMeans = new WekaSimpleKMeansCluster();
		wekaKMeans.setInputData(wekaDatosCluster);
		wekaKMeans.setK(clusterer.numberOfClusters());
		wekaKMeans.setSimpleKMeans(clusterer);

		int iCluster = launcher.clasificarInstanciaWeka(inst);

		Instance centroide = clusterer.getClusterCentroids().instance(iCluster);

		double dEuclidea = wekaDatosCluster.getDistanciaEuclidea(
				centroide.toDoubleArray(), attValues);
		
		Instance[] instCluster = wekaKMeans.getInstancesDCluster(data.enumerateInstances(), iCluster);
		Instance[] similares = wekaDatosCluster.getSimiliarInstance(instCluster, inst.getInstance(), 5);
		
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(clusterer);
		
		/*
		 * Imprimir resultados:
		 * */
		System.out.println("ANçLISIS NUEVO ELEMENTO ALBUM");
		System.out.println("==============================\n");
		System.out.println("GŽneros y valores del album:");
		System.out.println("GŽnero         Peso");
		System.out.println("-------------------");
		System.out.println(inst.toString() + "\n");
		System.out.println(" > Album clasificado en el cluster #" + iCluster);
		System.out.println(" > Distancia Euclidea con centroide: " + dEuclidea);
		System.out.println("\nALBUMS RECOMENDADOS (5):");
		for(int i = 0; i < similares.length; i++) {
			WekaSRIInstance similar = new WekaSRIInstance(1.0, similares[i].toDoubleArray());
			double dEuclideaSimilar = wekaDatosCluster.getDistanciaEuclidea(attValues, similares[i].toDoubleArray());
			System.out.println(" > Instancia #" + i + ":   (dE: " + dEuclideaSimilar + ")");
			System.out.println();
			System.out.println("GŽnero         Peso");
			System.out.println("-------------------");
			System.out.println(similar.toString());
		}
		

	}

}
