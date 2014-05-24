package es.uclm.sri.scrape;

import es.uclm.sri.scrape.webscraping.ScrapingLastfm;

/**
 * F‡brica Scraping Lastfm
 * 
 * @author Sergio Navarro
 * */
public class FactoryScrapingLastfm extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingLastfm(rutaDestino);
    }

}