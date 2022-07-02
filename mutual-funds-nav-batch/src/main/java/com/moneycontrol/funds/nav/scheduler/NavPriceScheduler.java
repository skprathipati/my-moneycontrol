package com.moneycontrol.funds.nav.scheduler;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Component
@CommonsLog
public class NavPriceScheduler {
    private final String FILE_URL = "https://www.amfiindia.com/spages/NAVAll.txt";
    @Scheduled(cron = "0 0/5 * * * ?")
    public void retrieveNavPrice() throws Exception {
        log.info(">>>> NAV Price Scheduler Started at " + LocalDateTime.now());
        try (InputStream inputStream = new URL(FILE_URL).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Stream<String> navStream = reader.lines();
            navStream.forEach(s -> {
                if (s.matches("[0-9].*")) {
                    log.info(s);
                }
            });
        }
        log.info(">>>> NAV Price Scheduler Completed at " + LocalDateTime.now());
    }
}
