package com.victormiranda.mani.scraper.processor;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.Transaction;
import com.victormiranda.mani.scraper.bean.NavigationSession;

import java.util.List;

@FunctionalInterface
public interface TransactionProcessor {

    List<Transaction> processTransactions(final AccountInfo accountInfo, final NavigationSession navigationSession);
}
