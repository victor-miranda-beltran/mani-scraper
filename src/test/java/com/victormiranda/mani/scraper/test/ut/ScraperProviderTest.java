package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.scraper.processor.ptsb.PTSBAccountProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBLoginProcessor;
import com.victormiranda.mani.scraper.processor.ptsb.PTSBTransactionProcessor;
import com.victormiranda.mani.scraper.type.ScraperProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * @see com.victormiranda.mani.scraper.type.ScraperProvider
 */
public class ScraperProviderTest {

    @Test(expected = IllegalArgumentException.class)
    public void findUnknownScrapperProvider() {
        ScraperProvider.getByName("unexistent");
    }

    @Test
    public void findKnownScrapperProvider() {
        final ScraperProvider scraperProvider = ScraperProvider.getByName("PTSB");

        Assert.assertEquals(scraperProvider.getLoginProcessor().getTypeName(),
                PTSBLoginProcessor.class.getTypeName());

        Assert.assertEquals(scraperProvider.getAccountProcessor().getTypeName(),
                            PTSBAccountProcessor.class.getTypeName());

        Assert.assertEquals(scraperProvider.getTramsactionProcessor().getTypeName(),
                PTSBTransactionProcessor.class.getTypeName());
    }


}
