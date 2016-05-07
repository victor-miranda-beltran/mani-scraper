package com.victormiranda.mani.scraper.type;


import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.processor.TransactionProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBLoginProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBTransactionProcessor;

public enum ScraperProvider {
    PTSB(PTSBLoginProcessor.class, PTSBAccountProcessor.class, PTSBTransactionProcessor.class);

    final Class<? extends LoginProcessor> loginProcessor;
    final Class<? extends AccountProcessor> accountProcessor;
    final Class<? extends TransactionProcessor> tramsactionProcessor;

    ScraperProvider(final Class<? extends LoginProcessor> loginProcessorClass,
                    final Class<? extends AccountProcessor> accountProcessor,
                    final Class<? extends TransactionProcessor> transactionProcessor) {
        this.loginProcessor = loginProcessorClass;
        this.accountProcessor = accountProcessor;
        this.tramsactionProcessor = transactionProcessor;
    }

    public Class<? extends LoginProcessor> getLoginProcessor() {
        return loginProcessor;
    }

    public Class<? extends AccountProcessor> getAccountProcessor() {
        return accountProcessor;
    }

    public Class<? extends TransactionProcessor> getTramsactionProcessor() {
        return tramsactionProcessor;
    }

    public static ScraperProvider getByName(final String name) {
        for (ScraperProvider scraperProvider : ScraperProvider.values()) {
            if (scraperProvider.name().equals(name)) {
                return scraperProvider;
            }
        }

        throw new IllegalArgumentException("Scraper not known : " + name);
    }
}
