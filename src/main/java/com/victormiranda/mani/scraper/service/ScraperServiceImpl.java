package com.victormiranda.mani.scraper.service;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.bean.SynchronizationResult;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.processor.TransactionProcessor;
import com.victormiranda.mani.scraper.type.ScraperProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
            throws SynchronizationException {

        validateRequest(syncRequest);

        final ScraperProvider scraperProvider =
                ScraperProvider.getByName(syncRequest.getCredentials().getBankProvider().name());

        final LoginProcessor loginProcessor = applicationContext.getBean(scraperProvider.getLoginProcessor());
        final AccountProcessor accountProcessor = applicationContext.getBean(scraperProvider.getAccountProcessor());
        final TransactionProcessor transactionProcessor = applicationContext.getBean(scraperProvider.getTramsactionProcessor());

        final LoggedNavigationSession navigationSession;

        try {
            navigationSession = loginProcessor.processLogin(syncRequest.getCredentials());
        } catch (LoginException loginException) {
            throw new SynchronizationException(loginException);
        }

        final Set<AccountInfo> accountsDetected = accountProcessor.processAccounts(navigationSession);

        for (AccountInfo accountInfo : accountsDetected) {
            if (isUpdateNeeded(accountInfo, syncRequest.getAccounts())) {
                accountInfo.getTransactions().addAll(transactionProcessor.processTransactions(accountInfo, navigationSession));
            }
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

    private boolean isUpdateNeeded(final AccountInfo accountInfo, final Set<AccountInfo> knownAccounts) {
        final Optional<AccountInfo> knownAccount = knownAccounts.stream()
                .filter(a -> accountInfo.getAccountNumber().equals(a.getAccountNumber()))
                .findFirst();

        return !knownAccount.isPresent() ||
                !knownAccount.get().getAvailableBalance().equals(accountInfo.getAvailableBalance()) ||
                !knownAccount.get().getCurrentBalance().equals(accountInfo.getCurrentBalance());
    }

}
