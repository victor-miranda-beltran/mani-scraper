package com.victormiranda.mani.scraper.test.ut;

import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.scraper.App;
import com.victormiranda.mani.scraper.SynchronizationException;
import com.victormiranda.mani.scraper.service.ScraperService;
import com.victormiranda.mani.scraper.service.ScraperServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


public class ScraperServiceTest {

    @Test(expected=SynchronizationException.class)
    public void testSyncNullRequest() throws SynchronizationException {

        final ScraperService scraperService = new ScraperServiceImpl();

        scraperService.scrape(null);
    }

}
