package scrape;

import java.io.Serializable;

import es.uclm.sri.sis.scrape.AbstractFactoryScraping;
import es.uclm.sri.sis.scrape.AbstractWebScraping;
import es.uclm.sri.sis.scrape.FactoryScrapingLastfm;

public class ScrapeLastfm implements Serializable {

    public static void lanzarScraping() {

        AbstractFactoryScraping factoryScraping = new FactoryScrapingLastfm();
        AbstractWebScraping webScraping = factoryScraping
                .launcherScraping("/Users/sergionavarro/PFC/test_lastfm.csv");

    }

}
