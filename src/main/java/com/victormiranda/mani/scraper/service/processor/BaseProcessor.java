package com.victormiranda.mani.scraper.service.processor;

import com.victormiranda.mani.scraper.bean.NavigationSession;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

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
                    .cookies(navigationSession.getCookies())
                    .execute();

            navigationSession.getCookies().putAll(res.cookies());

            parsedDocument = res.parse();
        } catch (IOException e) {
            LOGGER.error("Error processing url " + url, e);
        }
        return parsedDocument;
    }

    private void delay(final long i) {
        try {
            Thread.sleep((long) (i * Math.random()));
        } catch (InterruptedException e) {
            LOGGER.error("Error in delay", e);
        }
    }
}

