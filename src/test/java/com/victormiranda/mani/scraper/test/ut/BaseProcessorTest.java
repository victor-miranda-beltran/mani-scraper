package com.victormiranda.mani.scraper.test.ut;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

public class BaseProcessorTest {

    public Document getDocumentFromString(final String page) throws IOException {
        final InputStream is = getInputStream(page);

        return Jsoup.parse(is, "UTF-8", is.toString());
    }

    protected InputStream getInputStream(final String path) {
        return this.getClass().getResourceAsStream(path);
    }

}
