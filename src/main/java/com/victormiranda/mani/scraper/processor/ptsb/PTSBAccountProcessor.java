package com.victormiranda.mani.scraper.processor.ptsb;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.BaseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PTSBAccountProcessor extends BaseProcessor implements AccountProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PTSBAccountProcessor.class.getName());

    @Override
    public Set<AccountInfo> processAccounts(final NavigationSession navigationSession) {
        LOGGER.info("Processing accounts");

        return new HashSet<>();
    }


}
