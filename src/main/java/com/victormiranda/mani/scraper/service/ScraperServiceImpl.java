package com.victormiranda.mani.scraper.service;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.bean.SynchronizationResult;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.processor.TransactionProcessor;
import com.victormiranda.mani.scraper.type.ScraperProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public final class ScraperServiceImpl implements ScraperService {


    private final ApplicationContext applicationContext;

    @Autowired
    public ScraperServiceImpl(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public SynchronizationResult scrape(SynchronizationRequest syncRequest)
            throws SynchronizationException, LoginException {

        validateRequest(syncRequest);

        final ScraperProvider scraperProvider =
                ScraperProvider.getById(syncRequest.getCredentials().getBankProvider().name());

        final LoginProcessor loginProcessor = applicationContext.getBean(scraperProvider.getLoginProcessor());
        final AccountProcessor accountProcessor = applicationContext.getBean(scraperProvider.getAccountProcessor());
        final TransactionProcessor transactionProcessor = applicationContext.getBean(scraperProvider.getTramsactionProcessor());

        final NavigationSession navigationSession = loginProcessor.processLogin(syncRequest.getCredentials());

        final Set<AccountInfo> accountsDetected = accountProcessor.processAccounts(navigationSession);

        for (AccountInfo accountInfo : accountsDetected) {
            accountInfo.getTransactions().addAll(transactionProcessor.processTransactions(accountInfo, navigationSession));
        }

        return new SynchronizationResult(accountsDetected, !accountsDetected.isEmpty());
    }


    private void validateRequest(SynchronizationRequest syncRequest) throws SynchronizationException {

        if (syncRequest == null) {
            throw  new SynchronizationException("SyncRequest is null");
        }

        if (syncRequest.getCredentials() == null) {
            throw new SynchronizationException("empty credentials");
        }
    }

}
