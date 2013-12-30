package es.uclm.sri.clustering;

import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import es.uclm.sri.sis.KSistema;
import es.uclm.sri.sis.utilidades.FicheroDPropiedades;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class ClustererSri {
    
    private static Instances data = null;
    private static SimpleKMeans clusterer = null;
    
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
    
    private void cargarPropiedadesBD() {
        try {
            properties = new FicheroDPropiedades(KSistema.Recursos.PATH_DATABASE_PROPERTIES);
            properties.cargarPropiedades();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
