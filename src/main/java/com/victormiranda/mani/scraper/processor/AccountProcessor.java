package com.victormiranda.mani.scraper.processor;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;

import java.util.Set;

@FunctionalInterface
public interface AccountProcessor {

    Set<AccountInfo> processAccounts(final LoggedNavigationSession navigationSession);
}
