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

import es.uclm.sri.clustering.ClustererSri;
import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.log.Log;
import es.uclm.sri.sis.utilidades.Utils;
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

/**
 * Clase principal para construir el clustering
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public class WekaClusteringFacade {

	private static Instances data = null;
	private static SimpleKMeans clusterer = null;
	private static WekaSimpleKMeansCluster wekaKMeans = null;
	
	private static WekaClusteringFacade instance;
	
	/**
	 * Constructor partiendo de un archivo ARFF con las instancias
	 * de clustering formadas.
	 * 
	 * @param pathFileARFF
	 * 			Ruta del fichero ARFF
	 * @exception Exception
	 * */
	public WekaClusteringFacade(String pathFileARFF) throws Exception {
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
	public WekaClusteringFacade() {
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
            query.setDatabaseURL(properties.getProperty("database.url"));
            query.setUsername(properties.getProperty("database.username"));
            query.setPassword(properties.getProperty("database.password"));

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
	 * Patron Singleton
	 * */
	public static WekaClusteringFacade getInstance() {
        Log.log("====== INVOCANDO A SISTEMA DE CLUSTERING WEKA ======", 2);
        createInstance();
        return instance;
    }
	
	private static void createInstance() {
        if (instance == null) {
            synchronized (WekaClusteringFacade.class) {
                if (instance == null) {
                    instance = new WekaClusteringFacade();
                }
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
            String database = properties.getProperty("database.url");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            
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
		Log.log("Construyendo clustering con Weka para los parámetros configurados", 1);
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
		
		ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(clusterer);
	}
	
	public WekaSRIInstance[] generarRecomendacionesWeka(WekaSRIInstance inst) throws Exception {
        Log.log("Generando recomendaciones del clustering", 1);
        AnalysisFactory.buildFactory();

        WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
                .createRawData(data);
        wekaKMeans = new WekaSimpleKMeansCluster();
        wekaKMeans.setInputData(wekaDatosCluster);
        wekaKMeans.setK(clusterer.numberOfClusters());
        wekaKMeans.setSimpleKMeans(clusterer);

        int iCluster = clasificarInstanciaWeka(inst);
        Log.log("Cluster #" + iCluster, 1);
        Instance centroide = clusterer.getClusterCentroids().instance(iCluster);
        
        WekaSRIInstance[] instCluster = wekaKMeans.getWekaSRIInstancesDCluster(data.enumerateInstances(), iCluster);
        WekaSRIInstance[] resultados = wekaDatosCluster.getSimiliarWekaSRIInstance(instCluster, inst.getInstance(), 10);
        Log.log("Recomendaciones del clustering: ", 1);
        Log.log(WekaUtilities.toStringRecomendaciones(resultados), 1);
        return resultados;
    }
    
    public WekaSRIInstance[] generarRecomendacionesWekaAll(WekaSRIInstance inst) throws Exception {
        Log.log("Generando recomendaciones del clustering", 1);
        AnalysisFactory.buildFactory();

        WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
                .createRawData(data);
        wekaKMeans = new WekaSimpleKMeansCluster();
        wekaKMeans.setInputData(wekaDatosCluster);
        wekaKMeans.setK(clusterer.numberOfClusters());
        wekaKMeans.setSimpleKMeans(clusterer);

        int iCluster = clasificarInstanciaWeka(inst);
        Log.log("Cluster #" + iCluster, 1);
        Instance centroide = clusterer.getClusterCentroids().instance(iCluster);
        
        WekaSRIInstance[] instCluster = wekaKMeans.getWekaSRIInstancesDCluster(data.enumerateInstances(), iCluster);
        WekaSRIInstance[] resultados = wekaDatosCluster.getSimiliarWekaSRIInstance(instCluster, inst.getInstance());
        Log.log("Se devuelven todas las recomendaciones del cluster ordenadas por afinidad");
        return resultados;
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
	public int clasificarInstanciaWeka(WekaSRIInstance wekaInst)
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
	
	public Instances getData() {
		return this.data;
	}
	
	public SimpleKMeans getClusterer() {
		return this.clusterer;
	}
	
	public WekaSimpleKMeansCluster getWekaKMeans() {
		return this.wekaKMeans;
	}

}
