package com.victormiranda.mani.scraper.processor;


import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.exception.LoginException;

@FunctionalInterface
public interface LoginProcessor {

    NavigationSession processLogin(final Credentials credentials) throws LoginException;

}
