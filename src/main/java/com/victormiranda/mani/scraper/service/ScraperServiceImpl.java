package com.victormiranda.mani.scraper.service;


import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.bean.SynchronizationResult;
import com.victormiranda.mani.scraper.SynchronizationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public final class ScraperServiceImpl implements ScraperService {

    @Override
    public SynchronizationResult scrape(SynchronizationRequest syncRequest)
            throws SynchronizationException {

        if (syncRequest == null) {
            throw new SynchronizationException("synchronization request is null");
        }

        return new SynchronizationResult(new HashSet<>(), false);
    }

}
