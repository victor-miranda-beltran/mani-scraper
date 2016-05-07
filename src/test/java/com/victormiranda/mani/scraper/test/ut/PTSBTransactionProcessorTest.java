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
import java.util.Set;

public class PTSBTransactionProcessorTest extends BaseProcessorTest{

    public static final AccountInfo DUMMY_ACCOUNT = new AccountInfo("dummyAccount", "2134", "1234", BigDecimal.ONE, BigDecimal.ONE, LocalDate.now(), new HashSet<>());

    final TransactionProcessor transactionProcessor = new PTSBTransactionProcessorMock();
    final NavigationSession navigationSession = new NavigationSession();

    @Test
    public void testFetchAccounts() throws SynchronizationException, IOException, LoginException {
        final List<Transaction> transactions = transactionProcessor.processTransactions(DUMMY_ACCOUNT, navigationSession);

        Assert.assertTrue(!transactions.isEmpty());
    }

    @Test
    public void testDatesProcessedFromDescription() {
        final List<Transaction> transactions = transactionProcessor.processTransactions(DUMMY_ACCOUNT, navigationSession);
        String transactionUid = "permanenttsb Current695605/04/2016 00:00:00POS NOWTV.COM/BI 02/04 18.850012556.60";

        final Transaction transactionWithDateInDescription = transactions.stream()
                .filter(t -> transactionUid.equals(t.getTransactionUID()))
                .findFirst()
                .get();

        Assert.assertEquals(transactionWithDateInDescription.getDate(), LocalDate.of(2016,4,2));
    }

}

class PTSBTransactionProcessorMock extends PTSBTransactionProcessor {

    public Document parse(URL url, Connection.Method method, NavigationSession navigationSession) {
        return ProcessorMock.parse(url);
    }
}

