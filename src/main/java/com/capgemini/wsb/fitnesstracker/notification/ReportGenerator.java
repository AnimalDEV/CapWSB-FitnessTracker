package com.capgemini.wsb.fitnesstracker.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class ReportGenerator {

    //@Scheduled(cron = "0 0 1 * * MON")
    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void writeToStdOut() {
        log.info("REPORT!!!!");
        //javaMailSender.send();
    }
}
