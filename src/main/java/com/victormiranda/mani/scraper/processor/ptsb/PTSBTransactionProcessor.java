package com.victormiranda.mani.scraper.processor.ptsb;

import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.bean.Transaction;
import com.victormiranda.mani.scraper.bean.NavigationSession;
import com.victormiranda.mani.scraper.processor.BaseProcessor;
import com.victormiranda.mani.scraper.processor.TransactionProcessor;
import com.victormiranda.mani.scraper.type.PTSBUrl;
import com.victormiranda.mani.type.TransactionFlow;
import com.victormiranda.mani.type.TransactionStatus;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class PTSBTransactionProcessor extends BaseProcessor implements TransactionProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PTSBTransactionProcessor.class.getName());

    private static final Pattern DESCRIPTION_DATE_PATTERN = Pattern.compile(".*(\\d{2}/\\d{2}).*");
    private static final DateTimeFormatter descriptionFieldDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter dateFieldDateFormatter = DateTimeFormatter.ofPattern("dd MMM yy");

    @Override
    public List<Transaction> processTransactions(final AccountInfo accountInfo, final NavigationSession navigationSession) {
        URL url = PTSBUrl.getURL(accountInfo.getLastSynced()).url;

        final Map<String, String> accountParam =  new HashMap<>(1);
        accountParam.put("{uid}", accountInfo.getUid());

        final Document document = parse(expandURL(url, accountParam), Connection.Method.GET, navigationSession);

        final List<Transaction> transactions = new ArrayList<>();

        final Elements rawTransactions = document.select("table.header-big tbody tr");

        for (Element e : rawTransactions) {
            final Transaction transactionProcessed = getTransaction(e, TransactionStatus.NORMAL);

            LOGGER.info("Processed transaction " + transactionProcessed);

            transactions.add(transactionProcessed);
        }

        transactions.addAll(processPendingTransactions(document, navigationSession));

        return transactions;
    }

    private List<Transaction> processPendingTransactions(final Document document, final NavigationSession navigationSession) {
        final List<Transaction> pendingTransactions = new ArrayList<>();

        final Elements existentPendingCandidates = document.select(".module-sub-nav a");
        for (Element candidate : existentPendingCandidates) {
            final String href = candidate.attr("href");

            if (href.contains("PendingTransactions?accountId=") && candidate.text().matches(".*[(][0-9]{1,3}[)]")) {
                final Document pendingsDoc = parse(PTSBUrl.ACCOUNT_PENDINGS.url, Connection.Method.GET, navigationSession);
                final Elements rawTransactions = pendingsDoc.select("table tbody tr");

                pendingTransactions.addAll(
                        rawTransactions.stream()
                                .map(e -> getTransaction(e, TransactionStatus.PENDING))
                                .collect(Collectors.toList()));
            }
        }

        return pendingTransactions;
    }

    private Transaction getTransaction(final Element e, final TransactionStatus transactionStatus) {
        final String transactionUID = e.attr("data-uid");
        final String description = e.select(".desc").text();
        final String valIn = e.select("[data-money=in]").text();
        final String valOut = e.select("[data-money=out]").text();

        final TransactionFlow transactionFlow;
        final BigDecimal transactionAm;

        if (valIn.length() == 0) {
            transactionFlow = TransactionFlow.OUT;
            transactionAm = BaseProcessor.money(valOut);
        } else {
            transactionFlow = TransactionFlow.IN;
            transactionAm = BaseProcessor.money(valIn);
        }

        final LocalDate transactionDate = processDate(e.select(".date").text(), description);

        return new Transaction(
                transactionUID, description, transactionDate, transactionFlow, transactionAm, transactionStatus);
    }

    private LocalDate processDate(final String dateFromDateField, final String description) {
        final LocalDate localDateFromDate =  LocalDate.parse(dateFromDateField, dateFieldDateFormatter);

        if (description == null || description.trim().length() == 0) {
            return localDateFromDate;
        }

        final Matcher descriptionMatcher = DESCRIPTION_DATE_PATTERN.matcher(description);

        if (!descriptionMatcher.matches()) {
            return localDateFromDate;
        }

        final String dateCleanedFromDescription = descriptionMatcher.group(1) + "/" + localDateFromDate.getYear();
        final LocalDate localDateFromDesc = LocalDate.parse(dateCleanedFromDescription, descriptionFieldDateFormatter);

        final long differenceBetweenDates = ChronoUnit.DAYS.between(localDateFromDesc, localDateFromDate);

        if (differenceBetweenDates > 0 && differenceBetweenDates < 4) {
            return localDateFromDesc;
        }

        return localDateFromDate;
    }
}
