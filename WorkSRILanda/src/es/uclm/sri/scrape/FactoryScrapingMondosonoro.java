package es.uclm.sri.scrape;

import es.uclm.sri.scrape.webscraping.ScrapingMondosonoro;

public class FactoryScrapingMondosonoro extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingMondosonoro(rutaDestino);
    }

}
