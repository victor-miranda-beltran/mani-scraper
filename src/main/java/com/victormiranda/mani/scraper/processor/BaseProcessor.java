package com.victormiranda.mani.scraper.processor;

import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.util.Validate;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class BaseProcessor {

    public static final long DEFAULT_DELAY_BETWEEN_REQUESTS = 2500;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProcessor.class.getName());

    public Document parse(final URL url, Connection.Method method, final NavigationSession navigationSession) {
        Document parsedDocument = null;
        try {

            delay(DEFAULT_DELAY_BETWEEN_REQUESTS);
            final Connection.Response res = Jsoup.connect(url.toString())
                    .followRedirects(true)
                    .method(method)
                    .data(navigationSession.getParams())
                    .cookies(navigationSession.getCookies())
                    .execute();

            navigationSession.getCookies().putAll(res.cookies());

            parsedDocument = res.parse();
        } catch (IOException e) {
            LOGGER.error("Error processing url " + url, e);
        }
        return parsedDocument;
    }

    public static BigDecimal money(String text) {
        if (!Validate.notEmpty(text)) {
            return BigDecimal.ZERO;
        }

        try {
            return new BigDecimal(text.replace("€","").replace(",",""));
        } catch (NumberFormatException e) {
            LOGGER.error("Error processing " + text);
            throw e;
        }
    }

    public static URL expandURL(final URL url, final Map<String, String> params) {
        URL destination = null;

        String urlDestination = url.toString();

        for (Map.Entry<String, String> e : params.entrySet()) {
            urlDestination = urlDestination.replace(e.getKey(), e.getValue());
        }

        try {
            destination = new URL(urlDestination);
        } catch (MalformedURLException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        return destination;
    }

    private void delay(final long i) {
        try {
            Thread.sleep((long) (i * Math.random()));
        } catch (InterruptedException e) {
            LOGGER.error("Error in delay", e);
            Thread.currentThread().interrupt();
        }
    }
}

