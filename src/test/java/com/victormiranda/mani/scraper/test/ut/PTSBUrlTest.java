package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.scraper.type.PTSBUrl;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * @see PTSBUrl
 */
public class PTSBUrlTest {

    @Test
    public void testExpandedSyncNeeded() {
        final LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);

        Assert.assertEquals(PTSBUrl.getURL(twoMonthsAgo), PTSBUrl.ACCOUNT_DETAILS_EXPANDED);
    }

    @Test
    public void testExpandedSyncNeededNullDate() {
        Assert.assertEquals(PTSBUrl.getURL(null), PTSBUrl.ACCOUNT_DETAILS_EXPANDED);
    }

    @Test
    public void testExpandedSyncNotNeeded() {
        final LocalDate twoDaysAgo = LocalDate.now().minusDays(2);

        Assert.assertEquals(PTSBUrl.getURL(twoDaysAgo), PTSBUrl.ACCOUNT_DETAILS);
    }
}
