package com.victormiranda.mani.scraper.service.processor;

import com.victormiranda.mani.scraper.bean.NavigationSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;

public class BaseProcessor {

    public final long DEFAULT_DELAY_BETWEEN_REQUESTS = 2500;

    private static final Logger LOG = LoggerFactory.getLogger(BaseProcessor.class.getName());

    public static BigDecimal money(String text) {
        try {
            return new BigDecimal(text.replace("€","").replace(",",""));
        } catch (Exception e) {
            LOG.error("Error processing " + text);
        }

        return BigDecimal.ZERO;
    }

    public Document parse(final URL url, Connection.Method method, final NavigationSession navigationSession) {
        Document parsedDocument = null;
        try {

            final Connection.Response res = Jsoup.connect(url.toString())
                    .followRedirects(true)
                    .method(method)
                    .cookies(navigationSession.getCookies())
                    .execute();

            navigationSession.getCookies().putAll(res.cookies());

            parsedDocument = res.parse();
        } catch (IOException e) {
            LOG.error("Error processing url " + url, e);
        }
        return parsedDocument;
    }

    protected URL expandURL(final URL url, final Map<String, String> params) {
        URL destination = null;

        String urlDestination = url.toString();

        for (Map.Entry<String, String> e : params.entrySet()) {
            urlDestination = urlDestination.replace(e.getKey(), e.getValue());
        }

        try {
            destination = new URL(urlDestination);
            LOG.error("Processed destination " + destination.toString());

        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
        }

        return destination;
    }


    protected void delay(final long i) {
        try {
            Thread.sleep((long) (i * Math.random()));
        } catch (InterruptedException e) {
            LOG.error("Error in delay", e);
        }
    }
}

