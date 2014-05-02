package es.uclm.sri.clustering.weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.WekaException;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.utilidades.Utils;

public class WekaClusteringLauncher {

	private static Instances data = null;
	private static SimpleKMeans clusterer = null;
	private static WekaSimpleKMeansCluster wekaKMeans = null;

//	public WekaClusteringLauncher() throws Exception {
//		new WekaClusteringLauncher(null);
//	}

	public WekaClusteringLauncher(String pathFileARFF) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(pathFileARFF));
		this.data = new Instances(reader);

		reader.close();

		executeWekaKMeans();
	}

	public void WekaClusteringModelLauncher(String pathModelWeka)
			throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(pathModelWeka));
		this.data = loader.getStructure();

		data.deleteStringAttributes();

		executeWekaKMeans();
	}
	
	public WekaClusteringLauncher() {
	    InstanceQuery query;
	    boolean ok = false;
        try {
            /********************************
             * Instances for Database
             ********************************/
            query = new InstanceQuery();
            //query.setUsername(properties.getValorPropiedad("username"));
            //query.setPassword(properties.getValorPropiedad("password"));
            query.setUsername("postgres");
            query.setPassword("root");
            query.setQuery("SELECT * FROM \"PESOSALBUM\"");
            
            Log.log("Todo va bien. Estamos conectando con la base de datos para traer los pesos de los albums...");
            
            data = query.retrieveInstances();
            volcarDatsets(data);
            executeWekaKMeans();
            
            ok = true;
        } catch (WekaException e) {
            e.printStackTrace();
            Log.log(e, "Excepci—n Weka! " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.log(e, "Excepci—n E/S! " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "Excepci—n General! " + e.getMessage());
        } finally {
            if (ok) {
                Log.log("OK! Construcci—n weka-clustering terminada");
            }
        }
	}
	
	protected ResultSet connectToDataBase() {
	    ResultSet results = null;
        try {
            /*******************************
             * General from Database
             *******************************/
            String driverPostgre = "jdbc:postgresql";
            String database = "//localhost:5432/SRIBDATOS";
            String username = "postgres";
            String password = "root";
            
            // Create and Check Connection
            Class.forName("org.postgresql.Driver");
            Connection db = DriverManager.getConnection(driverPostgre + ":" + database, username, password);
            DatabaseMetaData dbmd = db.getMetaData();
            
            // Query
            Statement sql = db.createStatement();
            results = sql.executeQuery("SELECT * FROM \"PESOSALBUM\"");
            
            results.close();
            db.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
	}

	protected void executeWekaKMeans() throws Exception {
	    Instances dataAux;
		String[] options = new String[2];
		options[0] = "-I"; // max. iterations
		options[1] = "500";

		// Algoritmo SimpleKMeans de Weka
		clusterer = new SimpleKMeans();
		clusterer.setOptions(options);
		clusterer.setNumClusters(8);
		clusterer.setSeed(10);
		
		/*
		 * Filtro para los atributos de Identificador (0) çlbum (1) Artista (2) y Id de Album (21)
		 * 
		 * */
		int[] attributes = {0, 1, 2, 21};
		Remove rm = new Remove();
		
		rm.setAttributeIndicesArray(attributes);
		rm.setInputFormat(data);
		dataAux = Filter.useFilter(data, rm);

		// Funci—n de c‡lculo de distancias: Euclidea
		EuclideanDistance df = new EuclideanDistance(dataAux);
		df.setAttributeIndices("first-last");
		df.setDontNormalize(false);
		df.setInvertSelection(false);

		clusterer.setDistanceFunction(df);

		// Construcci—n del clusterer con las instancias traidas
		Log.log("Estamos construyendo el clusterer. Parametros Weka-Clusterer:");
		Log.log(" -Algoritmo: SimpleKMeans");
		Log.log(" -Num. Clusters: 8");
		Log.log(" -Max. Iteraciones: 500");
		Log.log(" -Funci—n de distancias: Euclidea");
		clusterer.buildClusterer(dataAux);

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
	
	protected ArffSaver volcarDatsets(Instances data) throws IOException {        
        ArffSaver guardarARFF = new ArffSaver();        
        String arff = ".arff";
        String ext = Utils.convertirFecha(Calendar.getInstance().getTime(), "ddMMyyyyHHmmss");

        guardarARFF.setInstances(data);
        guardarARFF.setDestination(new FileOutputStream("temp/data_" + ext + arff));
        guardarARFF.writeBatch();
        
        Log.log("Datasets creados se han volcado en el fichero interno temp/data_"  + ext + arff);
        
        return guardarARFF;
    }

	public static void main(String[] args) throws Exception {
//		WekaClusteringLauncher launcher = null;
//		if (args.length != 1) {
//			launcher = new WekaClusteringLauncher(
//					"/Users/sergionavarro/PFC/PesosAlbums_Titulados.arff");
//		} else {
//			launcher = new WekaClusteringLauncher(args[0]);
//		}
	    
	    WekaClusteringLauncher launcher = new WekaClusteringLauncher();

		// launcher.generarWekaSimpleKMeans(data);

		double[] attValues = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

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

		double dEuclidea = wekaDatosCluster.getDistanciaEuclideaCentroide(
				centroide.toDoubleArray(), attValues);
		
		/*
		 * Elimino el id de la tabla del dataset
		 * */
		
		int[] attributes = {0};
        Remove rm = new Remove();
        
        rm.setAttributeIndicesArray(attributes);
        rm.setInputFormat(data);
        data = Filter.useFilter(data, rm);
		
		WekaSRIInstance[] instCluster = wekaKMeans.getWekaSRIInstancesDCluster(data.enumerateInstances(), iCluster);
		WekaSRIInstance[] similares = wekaDatosCluster.getSimiliarWekaSRIInstance(instCluster, inst.getInstance(), 5);
		
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
//			WekaSRIInstance similar = new WekaSRIInstance(1.0, similares[i].toDoubleArray());
//			double dEuclideaSimilar = wekaDatosCluster.getDistanciaEuclidea(attValues, similares[i].toDoubleArray());
//			System.out.println(" > Instancia #" + i + ":   (dE: " + dEuclideaSimilar + ")");
			System.out.println();
			System.out.println("GŽnero         Peso");
			System.out.println("-------------------");
			System.out.println(similares[i].toString());
		}
		

	}

}
