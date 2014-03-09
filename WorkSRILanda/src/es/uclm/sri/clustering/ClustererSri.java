package es.uclm.sri.clustering;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import es.uclm.sri.clustering.weka.AnalysisFactory;
import es.uclm.sri.clustering.weka.WekaDatosCluster;
import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.clustering.weka.WekaSimpleKMeansCluster;
import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;
import es.uclm.sri.sis.utilidades.Utils;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class ClustererSri {
    
    private static Instances data = null;
    private static SimpleKMeans clusterer = null;
    private static WekaSimpleKMeansCluster wekaKMeans = null;
    
    private static FicheroDPropiedades properties = null;
    
    private static ClustererSri instance;
    
    private ClustererSri() {
        cargarPropiedadesBD();
        
        InstanceQuery query;
        try {
            
            query = new InstanceQuery();
            query.setUsername(properties.getValorPropiedad("username"));
            query.setPassword(properties.getValorPropiedad("password"));
            query.setQuery("SELECT * FROM \"PESOSALBUM\"");
            
            data = query.retrieveInstances();
            
            construirCluterer(data);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ClustererSri getInstance() {
        createInstance();
        return instance;
    }
    
    private static void createInstance() {
        if (instance == null) {
            synchronized (ClustererSri.class) {
                if (instance == null) {
                    instance = new ClustererSri();
                }
            }
        }
    }
    
    protected void construirCluterer(Instances data) throws Exception {
        String[] options = new String[2];
        options[0] = "-I"; // max. iterations
        options[1] = "500";

        // Algoritmo SimpleKMeans de Weka
        clusterer = new SimpleKMeans();
        clusterer.setOptions(options);
        clusterer.setNumClusters(8);
        clusterer.setSeed(10);
        
        /*
         * Filtro para los atributos nombre de album y artista
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
        
        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(clusterer);
    }
    
    public WekaSRIInstance[] generarRecomendacionesWeka(WekaSRIInstance inst) throws Exception {

        AnalysisFactory.buildFactory();

        WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
                .createRawData(data);
        wekaKMeans = new WekaSimpleKMeansCluster();
        wekaKMeans.setInputData(wekaDatosCluster);
        wekaKMeans.setK(clusterer.numberOfClusters());
        wekaKMeans.setSimpleKMeans(clusterer);

        int iCluster = clasificarInstanciaWeka(inst);

        Instance centroide = clusterer.getClusterCentroids().instance(iCluster);
        
        WekaSRIInstance[] instCluster = wekaKMeans.getWekaSRIInstancesDCluster(data.enumerateInstances(), iCluster);
        return wekaDatosCluster.getSimiliarWekaSRIInstance(instCluster, inst.getInstance(), 10);
        
    }
    
    public WekaSRIInstance[] generarRecomendacionesWeka(Double[] attValues) throws Exception {

        WekaSRIInstance inst = new WekaSRIInstance(1.0, attValues);

        AnalysisFactory.buildFactory();

        WekaDatosCluster wekaDatosCluster = (WekaDatosCluster) AnalysisFactory
                .createRawData(data);
        wekaKMeans = new WekaSimpleKMeansCluster();
        wekaKMeans.setInputData(wekaDatosCluster);
        wekaKMeans.setK(clusterer.numberOfClusters());
        wekaKMeans.setSimpleKMeans(clusterer);

        int iCluster = clasificarInstanciaWeka(inst);

        Instance centroide = clusterer.getClusterCentroids().instance(iCluster);

        double dEuclidea = wekaDatosCluster.getDistanciaEuclideaCentroide(
                centroide.toDoubleArray(), Utils.toDoubleArray(attValues));
        
        WekaSRIInstance[] instCluster = wekaKMeans.getWekaSRIInstancesDCluster(data.enumerateInstances(), iCluster);
        return wekaDatosCluster.getSimiliarWekaSRIInstance(instCluster, inst.getInstance(), 10);
        
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
    
    private void cargarPropiedadesBD() {
        try {
            properties = new FicheroDPropiedades(KSistema.Recursos.PATH_DATABASE_PROPERTIES);
            properties.cargarPropiedades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
