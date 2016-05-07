package com.victormiranda.mani.scraper.type;


import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBLoginProcessor;

public enum ScraperProvider {
    PTSB(PTSBLoginProcessor.class, PTSBAccountProcessor.class);

    final Class<? extends LoginProcessor> loginProcessor;
    final Class<? extends AccountProcessor> accountProcessor;

    ScraperProvider(Class<? extends LoginProcessor> loginProcessorClass,
                    Class<? extends AccountProcessor> accountProcessor) {
        this.loginProcessor = loginProcessorClass;
        this.accountProcessor = accountProcessor;
    }

    public Class<? extends LoginProcessor> getLoginProcessor() {
        return loginProcessor;
    }

    public Class<? extends AccountProcessor> getAccountProcessor() {
        return accountProcessor;
    }

    public static ScraperProvider getById(final String id) {
        for (ScraperProvider scraperProvider : ScraperProvider.values()) {
            if (scraperProvider.name().equals(id)) {
                return scraperProvider;
            }
        }

        throw new IllegalArgumentException("Scraper not known : " + id);
    }
}
