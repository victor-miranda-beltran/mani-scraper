package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.scraper.type.PTSBUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("squid:S2187")
public class BaseProcessorTest {

    public static final Map<URL, String> mockPages = new HashMap<>();

    static {
        mockPages.put(PTSBUrl.LOGIN_FIRST_STEP_GET.url, "/ptsb/firstlogin.html");
        mockPages.put(PTSBUrl.LOGIN_FIRST_STEP_SEND.url, "/ptsb/secondlogin.html");
        mockPages.put(PTSBUrl.LOGIN_FINISH.url, "/ptsb/dashboard.html");
        mockPages.put(PTSBUrl.DASHBOARD.url, "/ptsb/dashboard.html");
        mockPages.put(PTSBUrl.ACCOUNT_DETAILS.url, "/ptsb/account-details.html");
        mockPages.put(PTSBUrl.ACCOUNT_DETAILS_EXPANDED.url, "/ptsb/account-details.html");
        mockPages.put(PTSBUrl.ACCOUNT_PENDINGS.url, "/ptsb/pendings.html");
    }

    public static Document getDocumentFromString(final String page) throws IOException {
        final InputStream is = getInputStream(page);

        return Jsoup.parse(is, "UTF-8", is.toString());
    }

    protected static InputStream getInputStream(final String path) {
        return BaseProcessorTest.class.getResourceAsStream(path);
    }

}

class ProcessorMock  {

    public static Document parse(URL url) {
        Document documentFromString = null;

        try {
            documentFromString = BaseProcessorTest.getDocumentFromString(BaseProcessorTest.mockPages.get(url));
            return documentFromString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return documentFromString;
    }
}
