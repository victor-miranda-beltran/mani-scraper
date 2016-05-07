package com.victormiranda.mani.scraper.controller;

import com.victormiranda.mani.bean.SynchronizationRequest;
import com.victormiranda.mani.bean.SynchronizationResult;
import com.victormiranda.mani.scraper.exception.SynchronizationException;
import com.victormiranda.mani.scraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScraperController {

    private final ScraperService scraperService;

    @Autowired
    public ScraperController(final ScraperService scraperService) {
        this.scraperService = scraperService;
    }

    @RequestMapping("/scrape")
    public SynchronizationResult scrape(@RequestBody  final SynchronizationRequest synchronizationRequest)
            throws SynchronizationException {

        return scraperService.scrape(synchronizationRequest);
    }


}
