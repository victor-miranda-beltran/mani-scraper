package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.Transaction;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.TransactionProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBTransactionProcessor;
import com.victormiranda.mani.type.TransactionStatus;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PTSBTransactionProcessorTest extends BaseProcessorTest{

    public static final AccountInfo DUMMY_ACCOUNT = new AccountInfo.Builder()
            .withName("dummy account")
            .withUid("1234")
            .withAccountNumber("1234")
            .build();


    final TransactionProcessor transactionProcessor = new PTSBTransactionProcessorMock();
    final NavigationSession navigationSession = new NavigationSession();

    @Test
    public void testFetchTransactions() throws SynchronizationException, IOException, LoginException {
        final List<Transaction> transactions = transactionProcessor.processTransactions(DUMMY_ACCOUNT, Optional.empty(), navigationSession);

        Assert.assertTrue(!transactions.isEmpty());
    }

    @Test
    public void testFetchPendingTransactions() throws SynchronizationException, IOException, LoginException {
        final List<Transaction> transactions = transactionProcessor.processTransactions(DUMMY_ACCOUNT, Optional.of(LocalDate.now()), navigationSession);

        long count = transactions.stream().filter(t -> t.getStatus() == TransactionStatus.PENDING).count();

        Assert.assertTrue(count == 9);
    }


}

class PTSBTransactionProcessorMock extends PTSBTransactionProcessor {

    public Document parse(URL url, Connection.Method method, NavigationSession navigationSession) {
        return ProcessorMock.parse(url);
    }
}

