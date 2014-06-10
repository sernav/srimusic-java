package main.java.es.uclm.sri.sis.dataswap;

import main.java.es.uclm.sri.clustering.weka.WekaSRIInstance;
import main.java.es.uclm.sri.sis.entidades.Recomendacion;

/**
 * El dataswap de recomiendación es la pasarela entre un diferentes instancias que pueden
 * contener una recomendación (WekaSRIInstance) y la entidad Recomendación del sistema.
 * 
 * @author Sergio Navarro
 * */
public class DSRecomendacion extends AbstractDataSwap {
    
    private Recomendacion recomendacion;
    private WekaSRIInstance instance;
    private String usuario;

    public DSRecomendacion(Object object, String usuario) {
        super();
        this.recomendacion = new Recomendacion();
        this.usuario = usuario;
        
        if (object instanceof WekaSRIInstance) {
            this.instance = (WekaSRIInstance) object;
        }
    }

    public Object generarDataSwap() {
    	this.recomendacion = new Recomendacion(instance, usuario);
        return this.recomendacion;
    }
    
    public Recomendacion getRecomendacionDs() {
    	return this.recomendacion;
    }
    
    public void setRecomendacionDs(WekaSRIInstance instance) {
    	this.instance = instance;
    }

}
