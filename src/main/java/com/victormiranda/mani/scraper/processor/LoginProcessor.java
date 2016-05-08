package com.victormiranda.mani.scraper.processor;


import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;

@FunctionalInterface
public interface LoginProcessor {

    LoggedNavigationSession processLogin(final Credentials credentials) throws LoginException;

}
