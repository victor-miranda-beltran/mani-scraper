package com.victormiranda.mani.scraper.bean;

import org.jsoup.nodes.Document;

public class LoggedNavigationSession extends NavigationSession {

    private final Document dashboard;

    public LoggedNavigationSession(final NavigationSession navigationSession, Document dashboard) {
        super(navigationSession);
        this.dashboard = dashboard;
    }

    public Document getDashboard() {
        return dashboard;
    }
}
