package es.uclm.sri.scrape;

import es.uclm.sri.scrape.webscraping.ScrapingMondosonoro;

/**
 * Factoría concreta de scraping en la web Mondosonoro
 * 
 * @author Sergio Navarro
 * */
public class FactoryScrapingMondosonoro extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingMondosonoro(rutaDestino);
    }

}
