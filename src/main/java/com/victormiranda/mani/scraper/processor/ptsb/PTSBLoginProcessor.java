package com.victormiranda.mani.scraper.processor.ptsb;


import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.bean.ptsb.PTSBCredentials;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;
import com.victormiranda.mani.scraper.processor.BaseProcessor;
import com.victormiranda.mani.scraper.processor.LoginProcessor;
import com.victormiranda.mani.scraper.util.Validate;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.victormiranda.mani.scraper.type.PTSBUrl.*;

@Component
public class PTSBLoginProcessor extends BaseProcessor implements LoginProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(PTSBLoginProcessor.class.getName());

    @Override
    public LoggedNavigationSession processLogin(final Credentials credentials) throws LoginException {
        final PTSBCredentials ptsbCredentials = (PTSBCredentials) credentials;

        if (!Validate.notEmpty(ptsbCredentials.getUser(), ptsbCredentials.getPassword(), ptsbCredentials.getPin())) {
            throw new LoginException("Empty username / password");
        }

        final NavigationSession navigationSession = processToken();

        fetchPinDigits(ptsbCredentials, navigationSession);


        return finishLogin(ptsbCredentials, navigationSession);
    }

    private NavigationSession processToken()  {
        final String selector = "input[name=__RequestVerificationToken]";

        final NavigationSession navigationSession = new NavigationSession();
        final Document bankResponse = parse(LOGIN_FIRST_STEP_GET.url, Connection.Method.GET, navigationSession);

        final Element masthead = bankResponse.select(selector).first();

        navigationSession.getParams().put("__RequestVerificationToken", masthead.attr("value"));

        return navigationSession;
    }


    private void fetchPinDigits(final PTSBCredentials credentials, NavigationSession navigationSession) throws LoginException {
        navigationSession.getParams().put("login-number", credentials.getUser());
        navigationSession.getParams().put("login-password", credentials.getPassword());

        final Document doc = parse(LOGIN_FIRST_STEP_SEND.url, Connection.Method.POST, navigationSession);

        navigationSession.getPrivateParams().put("requested-pin",
                new int[]{getDigit(doc, 1),getDigit(doc, 2),getDigit(doc, 3)}
        );

        navigationSession.getParams().remove("login-number");
        navigationSession.getParams().remove("login-password");
    }


    private LoggedNavigationSession finishLogin(PTSBCredentials credentials, final NavigationSession navigationSession) {
        final int[] requestedPin = (int[]) navigationSession.getPrivateParams().get("requested-pin");

        final String[] pin = credentials.getPin().split("");

        navigationSession.getParams().put("login-digit-1", pin[requestedPin[0] - 1]);
        navigationSession.getParams().put("login-digit-2", pin[requestedPin[1] - 1]);
        navigationSession.getParams().put("login-digit-3", pin[requestedPin[2] - 1]);

        final Document dashboard = parse(LOGIN_FINISH.url, Connection.Method.POST, navigationSession);

        navigationSession.getParams().remove("login-digit-1");
        navigationSession.getParams().remove("login-digit-2");
        navigationSession.getParams().remove("login-digit-3");

        return new LoggedNavigationSession(navigationSession, dashboard);
    }

    private int getDigit(final Document doc, final int num){
        try {
            String digit = doc.select("li.digit-" + num + " label").first().text();
            return Integer.valueOf(digit.replaceAll("\\D+",""));
        } catch (Exception e) {
            LOG.error("Error processing document \n" + doc);
            throw new IllegalStateException("wtf!", e);
        }
    }
}
