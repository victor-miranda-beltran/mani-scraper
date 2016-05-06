package com.victormiranda.mani.scraper.service;


import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.bean.SynchronizationResult;
import com.victormiranda.mani.scraper.SynchronizationException;

public interface ScraperService {

    SynchronizationResult scrape(SynchronizationRequest syncRequest) throws SynchronizationException;
}
