package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.JavaMailEmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class ReportGenerator {

    private JavaMailEmailSender javaMailEmailSender;

    private TrainingServiceImpl trainingService;

    @Scheduled(cron = "0 0 1 * * MON")
    public void sendTrainingNotificationEmails() {
        log.info("SENDING EMAILS");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Map<String, List<Training>> usersTrainings = trainingService.findAllTrainingsEndedAfter(cal.getTime())
                .stream().collect(Collectors.groupingBy(item -> item.getUser().getEmail()));


        usersTrainings.forEach((key, value) -> {
            EmailDto email = new EmailDto(
                    "from@example.com",
                    key,
                    "NUMBER OF TRAININGS THIS WEEK",
                    String.valueOf(value.size())
            );
            log.info(key);
            javaMailEmailSender.send(email);
        });
    }
}
