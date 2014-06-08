package es.uclm.sri.clustering.weka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.WekaException;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.utilidades.Utils;

/**
 * Clase principal para construir el clustering
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class WekaClusteringLauncher {

	private static Instances data = null;
	private static SimpleKMeans clusterer = null;
	private static WekaSimpleKMeansCluster wekaKMeans = null;
	
	/**
	 * Constructor partiendo de un archivo ARFF con las instancias
	 * de clustering formadas.
	 * 
	 * @param pathFileARFF
	 * 			Ruta del fichero ARFF
	 * */
	public WekaClusteringLauncher(String pathFileARFF) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(pathFileARFF));
		this.data = new Instances(reader);

		reader.close();

		executeWekaKMeans();
	}
	
	/**
	 * Constructor base.
	 * Conecta con la base de datos y trae los datos de la tabla donde se almacenan los pesos de album.
	 * Ejecuta la función <code>executeWekaKmeans</code> para obtener el dataset y el clustering.
	 * */
	public WekaClusteringLauncher() {
	    InstanceQuery query;
	    boolean ok = false;
	    Properties properties = getPropertiesBD();
        try {
        	/*
        	 * ****************************
        	 *    Instances for Database
        	 * ****************************
        	 * */
            query = new InstanceQuery();
            query.setUsername(properties.getProperty("username"));
            query.setPassword(properties.getProperty("password"));

            query.setQuery("SELECT * FROM \"PESOSALBUM\"");
            
            Log.log("Todo va bien. Estamos conectando con la base de datos para traer los pesos de los albums...");
            
            data = query.retrieveInstances();
            volcarDatsets(data);
            executeWekaKMeans();
            
            ok = true;
        } catch (WekaException e) {
            e.printStackTrace();
            Log.log(e, "Excepción Weka! " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.log(e, "Excepción E/S! " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e, "Excepción General! " + e.getMessage());
        } finally {
            if (ok) {
                Log.log("OK! Construcción weka-clustering terminada");
            }
        }
	}
	
	/**
	 * Función que lanza el clustering partiendo de un fichero con el modelo de datos Weka
	 * 
	 * @param pathModelWeka
	 * 			Ruta del fichero con el modelo
	 * 
	 * @exception Exception
	 * */
	public void WekaClusteringModelLauncher(String pathModelWeka)
			throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File(pathModelWeka));
		this.data = loader.getStructure();

		data.deleteStringAttributes();

		executeWekaKMeans();
	}
	
	/**
	 * Función para conectar a la base de datos del sistema y recoger los datos de
	 * la tabla PESOSALBUM para generar el data set.
	 * 
	 * @return Data set de datos con los pesos de albums: ResultSet
	 * */
	protected ResultSet connectToDataBase() {
		Properties properties = getPropertiesBD();
	    ResultSet results = null;
        try {
        	/*
        	 * *****************************
        	 *    Conexión general BD
        	 * *****************************
        	 * */
            String database = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            
            // Conexión
            Class.forName(properties.getProperty("driver"));
            Connection db = DriverManager.getConnection(database, username, password);
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
	
	/**
	 * Función que parametriza el proceso para crear el clusterting:
	 * 
	 * 	Algoritmo: Simple K Means
	 * 	Num. Clusters: 8
	 * 	Max. Iteraciones: 500
	 * 	Función de distancia: Euclidea
	 * 
	 * Descarta los atributos nominales: Álbum, Artista e id de album en BD.
	 * 
	 * Da valor al atributo SimpleKMeans.
	 * Siempre debe invocarse a esta función una vez tenemos un dataset de datos.
	 * 
	 * @exception Exception
	 * */
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
		 * Filtro para los atributos de Identificador (0) Álbum (1) Artista (2) y Id de Album (21)
		 * 
		 * */
		int[] attributes = {0, 1, 2, 21};
		Remove rm = new Remove();
		
		rm.setAttributeIndicesArray(attributes);
		rm.setInputFormat(data);
		dataAux = Filter.useFilter(data, rm);

		// Función de cálculo de distancias: Euclidea
		EuclideanDistance df = new EuclideanDistance(dataAux);
		df.setAttributeIndices("first-last");
		df.setDontNormalize(false);
		df.setInvertSelection(false);

		clusterer.setDistanceFunction(df);

		// Construcción del clusterer con las instancias traidas
		Log.log("Estamos construyendo el clusterer. Parametros Weka-Clusterer:");
		Log.log(" -Algoritmo: SimpleKMeans");
		Log.log(" -Num. Clusters: 8");
		Log.log(" -Max. Iteraciones: 500");
		Log.log(" -Función de distancias: Euclidea");
		clusterer.buildClusterer(dataAux);

	}
	
	/**
	 * Genera los datos para el atributo de la clase WekaSimpleKMeansCluster una vez
	 * tenemos el dataset de datos y el clustering formado.
	 * 
	 * @param data
	 * 			Objetdo de Instances de weka
	 * 
	 * @deprecated
	 * */
	protected void generarWekaSimpleKMeans(Instances data) {
		WekaDatosCluster wekaDatosCluster = new WekaDatosCluster(data);
		wekaKMeans = new WekaSimpleKMeansCluster();
		wekaKMeans.setInputData(wekaDatosCluster);
	}
	
	/**
	 * Para una instancia de WekaSRIInstance devuelve el cluster del clustering
	 * al que pertenece.
	 * Una vez tenemos el dataset y el clustering formado.
	 * 
	 * @param wekaInst
	 * 			Instancia de la clase WekaSRIInstance
	 * @return número de cluster: int
	 * @exception Exception
	 * */
	protected int clasificarInstanciaWeka(WekaSRIInstance wekaInst)
			throws Exception {
		int cluster = wekaKMeans.clusterInstance(wekaInst.getInstance());

		return cluster;
	}
	
	/**
	 * Función que evalua el clustering devolviendo los datos de cada cluster
	 * así como su centroide y número de elementos (%) en cada uno de ellos.
	 * 
	 * @return Información del clustering: String
	 * */
	protected String evaluadorClusterer() {
		ClusterEvaluation eval = new ClusterEvaluation();
		if (this.clusterer != null)
			eval.setClusterer(this.clusterer);

		return eval.clusterResultsToString();
	}
	
	/**
	 * Almacena las instacias de weka en un archivo ARFF propio de Weka.
	 * El fichero se guarda con la cabecera de fecha y hora en el directorio del proyecto TEMP.
	 * 
	 * @param data
	 * 			Objeto de Instances
	 * @return archivo para almacenar: ArffSaver
	 * @exception IOException
	 * */
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
	
	private Properties getPropertiesBD() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(KSistema.Recursos.PATH_DATABASE_PROPERTIES));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
