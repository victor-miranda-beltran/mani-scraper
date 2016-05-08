package com.victormiranda.mani.scraper.processor;


import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.Transaction;
import com.victormiranda.mani.scraper.bean.NavigationSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface TransactionProcessor {

    List<Transaction> processTransactions(final AccountInfo accountInfo,
                                          final Optional<LocalDate> lastSync,
                                          final NavigationSession navigationSession);
}
