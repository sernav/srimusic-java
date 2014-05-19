package es.uclm.sri.scrape;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import es.uclm.sri.sis.entidades.Album;

public abstract class AbstractWebScraping implements IHtmlScraping, Serializable {

    public String sitioWeb;
    public String url;
    public String subUrl;
    public int numPaginas;
    public String rutaDestino;

    public ArrayList<Album> listaAlbums = new ArrayList<Album>();

    public static Logger logger = Logger.getLogger(AbstractWebScraping.class);

    public AbstractWebScraping(String sitioWeb, String url, String subUrl,
            int numPaginas, String rutaDestino) {
        this.sitioWeb = sitioWeb;
        this.url = url;
        this.subUrl = subUrl;
        this.numPaginas = numPaginas;
        this.rutaDestino = rutaDestino;
    }

    public abstract void scrappingWeb(String url, String subUrl, int numPages,
            String destinyPath);

    public abstract void generarCSV();

    public ArrayList<Album> getListaAlbums() {
        return this.listaAlbums;
    }

    public String getRutaDestino() {
        return this.rutaDestino;
    }

    public String getSitioWeb() {
        return this.sitioWeb;
    }

    public Logger getLooger() {
        return this.logger;
    }

}
