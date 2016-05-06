package com.victormiranda.mani.scraper.service.processor;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.Credentials;
import com.victormiranda.mani.scraper.bean.NavigationSession;

import java.util.Set;

public interface AccountProcessor {

    Set<AccountInfo> processAccounts(final NavigationSession navigationSession);
}
