package es.uclm.sri.sis.scrape;

import es.uclm.sri.sis.scrape.webscraping.ScrapingMondosonoro;

public class FactoryScrapingMondosonoro extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingMondosonoro(rutaDestino);
    }

}
