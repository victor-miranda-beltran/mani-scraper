package com.victormiranda.mani.scraper.processor.ptsb;

import com.victormiranda.mani.bean.AccountInfo;
import com.victormiranda.mani.scraper.bean.LoggedNavigationSession;
import com.victormiranda.mani.scraper.processor.AccountProcessor;
import com.victormiranda.mani.scraper.processor.BaseProcessor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class PTSBAccountProcessor extends BaseProcessor implements AccountProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PTSBAccountProcessor.class.getName());

    @Override
    public Set<AccountInfo> processAccounts(final LoggedNavigationSession navigationSession) {
        LOGGER.info("Processing accounts");

        final Document document = navigationSession.getDashboard();

        final Elements accountCandidates = document.select(".module-account");
        final Set<AccountInfo> accounts = new HashSet<>(accountCandidates.size());

        for (Element e: accountCandidates) {
            final boolean isAccount = !e.select(".heading-general").isEmpty();
            if (isAccount) {
                final String uid = e.select(".heading-general a").attr("href").split("=")[1];
                final String name = document.select("option[value=" + uid + "]").first().text();
                final String accountNumber = name.substring(name.lastIndexOf(" ") + 1);
                final BigDecimal availableBalance = BaseProcessor.money(e.select(".funds .fund-1").text());
                final BigDecimal currentBalance = BaseProcessor.money(e.select(".funds .fund-2").text());

                accounts.add(new AccountInfo(name, accountNumber, uid, availableBalance, currentBalance, LocalDate.now(), new HashSet<>()));
            }
        }

        return accounts;
    }

}
