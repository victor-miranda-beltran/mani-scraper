package com.victormiranda.mani.scraper.test.ut;

import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBLoginProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBTransactionProcessor;
import com.victormiranda.mani.scraper.service.ScraperService;
import com.victormiranda.mani.scraper.service.ScraperServiceImpl;
import com.victormiranda.mani.scraper.type.ScraperProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class ScraperServiceTest {


    @Mock
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSyncWithKnownAccount() throws SynchronizationException, LoginException {

        final ScraperService scraperService = new ScraperServiceImpl(applicationContext);

        Mockito.when(
                applicationContext.getBean(ScraperProvider.PTSB.getLoginProcessor()))
            .thenAnswer( invocationOnMock -> new PTSBLoginProcessorMock());

        Mockito.when(
                applicationContext.getBean(ScraperProvider.PTSB.getAccountProcessor()))
                .thenAnswer( invocationOnMock -> new PTSBAccountProcessorMock());

        Mockito.when(
                applicationContext.getBean(ScraperProvider.PTSB.getTramsactionProcessor()))
                .thenAnswer( invocationOnMock -> new PTSBTransactionProcessorMock());

        final Set<AccountInfo> accounts = new HashSet<>();

        accounts.add(new AccountInfo.Builder().build());

        SynchronizationRequest synchronizationRequest = new SynchronizationRequest(
                BaseProcessorTest.DEMO_CREDENTIALS,
                accounts);

        scraperService.scrape(synchronizationRequest);
    }

}
