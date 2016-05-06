package com.victormiranda.mani.scraper.service.processor.ptsb;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.ptsb.PTSBCredentials;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.service.processor.AccountProcessor;
import com.victormiranda.mani.scraper.service.processor.BaseProcessor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.victormiranda.mani.scraper.type.PTSBUrl.*;

@Service
public class PTSBAccountProcessor extends BaseProcessor implements AccountProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(PTSBAccountProcessor.class.getName());

    @Override
    public Set<AccountInfo> processAccounts(final NavigationSession navigationSession) {

        return new HashSet<>();
    }


}
