package es.uclm.sri.sis.dataswap;

import es.uclm.sri.clustering.weka.WekaSRIInstance;
import es.uclm.sri.sis.entidades.Recomendacion;

public class DSRecomendacion extends AbstractDataSwap {
    
    private Recomendacion recomendacion;
    private WekaSRIInstance clusterInstance;

    public DSRecomendacion(Object object) {
        super();
        this.recomendacion = new Recomendacion();
        
        if (object.getClass() == WekaSRIInstance.class) {
            this.clusterInstance = (WekaSRIInstance) object;
        }
    }

    public Object generarDataSwap() {
        
        return null;
    }
    
    

}
