package com.victormiranda.mani.scraper.bean;

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class NavigationSession {

    protected final Map<String, String> params;
    protected final Map<String, Object> privateParams;
    protected final Map<String, String> cookies;

    public NavigationSession() {
        this.params = new HashMap<>();
        this.privateParams = new HashMap<>();
        this.cookies = new HashMap<>();
    }

    public NavigationSession(NavigationSession previousNavigationSession) {
        this.params = previousNavigationSession.getParams();
        this.privateParams = previousNavigationSession.getPrivateParams();
        this.cookies = previousNavigationSession.getCookies();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, Object> getPrivateParams() {
        return privateParams;
    }
}
