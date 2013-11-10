package beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import scrape.ScrapeLastfm;

import es.uclm.sri.sis.scrape.AbstractFactoryScraping;
import es.uclm.sri.sis.scrape.AbstractWebScraping;
import es.uclm.sri.sis.scrape.FactoryScrapingLastfm;

@ManagedBean(name = "scrapeBean")
@ViewScoped
public class ScrapeBean implements Serializable {
    
    private String rutaDirectorio;
    private String nombreCsv;
    
    public String getRutaDirectorio() {
        return rutaDirectorio;
    }
    
    public String getNombreCsv() {
        return nombreCsv;
    }
    
    public void setRutaDirectorio(String rutaDirectorio) {
        this.rutaDirectorio = rutaDirectorio;
    }
    
    public void setNombreCsv(String nombreCvs) {
        this.nombreCsv = nombreCvs;
    }

    public void lanzarScraping() {
        ScrapeLastfm.lanzarScraping();
    }

}
