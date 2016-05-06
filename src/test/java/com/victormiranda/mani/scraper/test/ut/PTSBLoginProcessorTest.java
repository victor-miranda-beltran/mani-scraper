package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.bean.ptsb.PTSBCredentials;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.service.processor.ptsb.PTSBLoginProcessor;
import com.victormiranda.mani.scraper.type.PTSBUrl;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PTSBLoginProcessorTest extends BaseProcessorTest{

    @Test(expected = LoginException.class)
    public void testLoginNullUserName() throws SynchronizationException, IOException, LoginException {

        final PTSBLoginProcessor loginProcessor = new PTSBLoginProcessorMock();
        final Credentials credentials = new PTSBCredentials(null, "password", "123456");

        loginProcessor.processLogin(credentials);
    }

    @Test(expected = LoginException.class)
    public void testLoginNullPassword() throws SynchronizationException, IOException, LoginException {

        final PTSBLoginProcessor loginProcessor = new PTSBLoginProcessorMock();
        final Credentials credentials = new PTSBCredentials("username", null, "123456");

        loginProcessor.processLogin(credentials);
    }

    @Test(expected = LoginException.class)
    public void testLoginPin() throws SynchronizationException, IOException, LoginException {

        final PTSBLoginProcessor loginProcessor = new PTSBLoginProcessorMock();
        final Credentials credentials = new PTSBCredentials("username", "password", null);

        loginProcessor.processLogin(credentials);
    }

    @Test
    public void testLogin() throws SynchronizationException, IOException, LoginException {

        final PTSBLoginProcessor loginProcessor = new PTSBLoginProcessorMock();
        final Credentials credentials = new PTSBCredentials("user", "password", "123456");

        final NavigationSession navigationSession = loginProcessor.processLogin(credentials);

        Assert.assertNotNull(navigationSession);
        Assert.assertNotNull(navigationSession.getPrivateParams().get("requested-pin"));
        Assert.assertEquals(((int [])navigationSession.getPrivateParams().get("requested-pin")).length, 3);
    }

    class PTSBLoginProcessorMock extends PTSBLoginProcessor {

        private final Map<URL, String> mockDocuments = new HashMap<>();

        public PTSBLoginProcessorMock() {
            mockDocuments.put(PTSBUrl.LOGIN_FIRST_STEP_GET.url, "/ptsb/firstlogin.html");
            mockDocuments.put(PTSBUrl.LOGIN_FIRST_STEP_SEND.url, "/ptsb/secondlogin.html");
            mockDocuments.put(PTSBUrl.LOGIN_FINISH.url, "/ptsb/dashboard.html");
            mockDocuments.put(PTSBUrl.ACCOUNT_DETAILS.url, "/ptsb/account-details.html");
            mockDocuments.put(PTSBUrl.ACCOUNT_DETAILS_EXPANDED.url, "/ptsb/account-details.html");
            mockDocuments.put(PTSBUrl.ACCOUNT_PENDINGS.url, "/ptsb/pendings.html");

        }

        @Override
        public Document parse(URL url, Connection.Method method, NavigationSession navigationSession) {
            Document documentFromString = null;

            try {
                documentFromString = getDocumentFromString(mockDocuments.get(url));
                return documentFromString;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return documentFromString;
        }

    }
}


