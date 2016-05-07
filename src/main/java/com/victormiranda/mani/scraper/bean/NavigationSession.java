package com.victormiranda.mani.scraper.bean;

import java.util.HashMap;
import java.util.Map;

public class NavigationSession {

    private final Map<String, String> params;
    private final Map<String, Object> privateParams;
    private final Map<String, String> cookies;

    public NavigationSession() {
        this.params = new HashMap<>();
        this.privateParams = new HashMap<>();
        this.cookies = new HashMap<>();
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
