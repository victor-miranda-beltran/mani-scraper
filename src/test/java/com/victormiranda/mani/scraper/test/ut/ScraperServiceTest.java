package com.victormiranda.mani.scraper.test.ut;

import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.service.ScraperService;
import com.victormiranda.mani.scraper.service.ScraperServiceImpl;
import org.junit.Test;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;


public class ScraperServiceTest {

    @Test(expected=SynchronizationException.class)
    public void testSyncNullRequest() throws SynchronizationException, LoginException {

        final ScraperService scraperService = new ScraperServiceImpl(new EmbeddedWebApplicationContext());

        scraperService.scrape(null);
    }

}
