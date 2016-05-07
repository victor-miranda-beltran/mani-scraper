package com.victormiranda.mani.scraper.service.processor;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.scraper.bean.NavigationSession;

import java.util.Set;

@FunctionalInterface
public interface AccountProcessor {

    Set<AccountInfo> processAccounts(final NavigationSession navigationSession);
}
