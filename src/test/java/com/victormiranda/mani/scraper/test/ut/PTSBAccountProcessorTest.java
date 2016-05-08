package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class PTSBAccountProcessorTest extends BaseProcessorTest{

    @Test
    public void testFetchAccounts() throws SynchronizationException, IOException, LoginException {

        final AccountProcessor ptsbAccountProcessor = new PTSBAccountProcessorMock();

        final Document document = getDocumentFromString("/ptsb/dashboard.html");
        final Set<AccountInfo> accountInfos = ptsbAccountProcessor.processAccounts(
                new LoggedNavigationSession(new NavigationSession(), document)
        );

        Assert.assertEquals(accountInfos.size(), 4);

        for (AccountInfo accountInfo : accountInfos) {
            Assert.assertNotNull(accountInfo.getAccountNumber());
            Assert.assertNotNull(accountInfo.getAvailableBalance());
            Assert.assertNotNull(accountInfo.getCurrentBalance());
            Assert.assertNotNull(accountInfo.getUid());
        }
    }

}

class PTSBAccountProcessorMock extends PTSBAccountProcessor {

    public Document parse(URL url, Connection.Method method, NavigationSession navigationSession) {
        return ProcessorMock.parse(url);
    }
}
