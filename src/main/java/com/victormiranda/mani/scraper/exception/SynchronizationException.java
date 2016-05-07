package com.victormiranda.mani.scraper.exception;

public class SynchronizationException extends Exception {

    public SynchronizationException(String message) {
        super(message);
    }

    public SynchronizationException(Exception e) {
        super(e);
    }
}
