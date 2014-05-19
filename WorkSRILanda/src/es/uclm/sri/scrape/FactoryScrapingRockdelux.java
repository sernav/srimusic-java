package es.uclm.sri.scrape;

import es.uclm.sri.scrape.webscraping.ScrapingRockdelux;

public class FactoryScrapingRockdelux extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingRockdelux(rutaDestino);
    }

}
