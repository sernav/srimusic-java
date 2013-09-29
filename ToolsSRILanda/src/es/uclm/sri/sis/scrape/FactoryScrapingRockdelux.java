package es.uclm.sri.sis.scrape;

import es.uclm.sri.sis.scrape.webscraping.ScrapingRockdelux;

public class FactoryScrapingRockdelux extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingRockdelux(rutaDestino);
    }

}
