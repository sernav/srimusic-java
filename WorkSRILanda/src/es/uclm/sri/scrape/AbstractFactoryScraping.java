package es.uclm.sri.scrape;

import java.io.Serializable;

/**
 * Abstract Factory (Pattern)
 * 
 * @author Sergio Navarro
 * */
public abstract class AbstractFactoryScraping implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7119927301498694121L;

    public abstract AbstractWebScraping launcherScraping(String rutaDestino);

}
