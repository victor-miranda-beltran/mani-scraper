package com.victormiranda.mani.scraper.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;

public enum PTSBUrl {
    LOGIN_FIRST_STEP_GET("https://www.open24.ie/online/login/"),
    LOGIN_FIRST_STEP_SEND("https://www.open24.ie/online"),
    LOGIN_FINISH("https://www.open24.ie/online/Login/Step2"),
    DASHBOARD("https://www.open24.ie/online/Accounts/Overview/Index"),
    ACCOUNT_DETAILS("https://www.open24.ie/online/Accounts/Details/Detail?accountId={uid}"),
    ACCOUNT_PENDINGS("https://www.open24.ie/online/Accounts/Details/PendingTransactions?accountId={uid}"),
    ACCOUNT_DETAILS_EXPANDED("https://www.open24.ie/online/Accounts/Details/RecentTransactions?accountId={uid}&months=12")
    ;

    public final URL url;

    private static final Logger LOGGER = LoggerFactory.getLogger(PTSBUrl.class.getName());


    PTSBUrl(String url) {
        URL url1;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            url1 = null;
        }
        this.url = url1;
    }

    public static PTSBUrl getURL(final Optional<LocalDate> lastSync) {
        LOGGER.info("Sync with lastSync " + lastSync);

        return !lastSync.isPresent() || lastSync.get().isBefore(LocalDate.now().minusMonths(1))
                ? PTSBUrl.ACCOUNT_DETAILS_EXPANDED
                : PTSBUrl.ACCOUNT_DETAILS;
    }

}
