package es.uclm.sri.sis.scrape;

import es.uclm.sri.sis.scrape.webscraping.ScrapingLastfm;

public class FactoryScrapingLastfm extends AbstractFactoryScraping {

    public AbstractWebScraping launcherScraping(String rutaDestino) {
        return new ScrapingLastfm(rutaDestino);
    }

}
