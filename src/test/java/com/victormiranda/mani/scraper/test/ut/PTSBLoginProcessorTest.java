package com.victormiranda.mani.scraper.test.ut;


import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.bean.ptsb.PTSBCredentials;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.service.processor.ptsb.PTSBLoginProcessor;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

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
        final Credentials credentials = new PTSBCredentials("user", "password", "873517");

        final NavigationSession navigationSession = loginProcessor.processLogin(credentials);

        Assert.assertNotNull(navigationSession);
        Assert.assertNotNull(navigationSession.getPrivateParams().get("requested-pin"));
        Assert.assertEquals(((int [])navigationSession.getPrivateParams().get("requested-pin")).length, 3);
    }

    private class PTSBLoginProcessorMock extends PTSBLoginProcessor {

        public Document parse(URL url, Connection.Method method, NavigationSession navigationSession) {
            return ProcessorMock.parse(url);
        }
    }
}


