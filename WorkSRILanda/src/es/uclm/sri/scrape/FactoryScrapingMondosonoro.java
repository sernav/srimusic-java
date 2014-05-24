package es.uclm.sri.scrape;

import es.uclm.sri.scrape.webscraping.ScrapingMondosonoro;

/**
 * F‡brica Scraping Mondosonoro
 * 
 * @author Sergio Navarro
 * */
public class FactoryScrapingMondosonoro extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingMondosonoro(rutaDestino);
    }

}
