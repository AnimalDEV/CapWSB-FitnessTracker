package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainingServiceImplTest {
    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private UserServiceImpl userService;

    @Transactional
    @Test
    public void testShouldCreateTraining() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
       User user = userService.createUser(createUserData);
        Training entity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        // when
        Training createdTraining = trainingService.createTraining(entity);
        // then
        Assertions.assertThat(createdTraining).isNotNull();
    }


    @Transactional
    @Test
    public void testShouldUpdateTraining() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User user = userService.createUser(createUserData);
        Training entity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training createdTraining = trainingService.createTraining(entity);
        Double newDistance = 14.2;
        //when
        Optional<Training> updatedTraining = trainingService.updateTrainingDistance(createdTraining.getId(), newDistance);
        // then
        Assertions.assertThat(updatedTraining.get().getDistance()).isEqualTo(newDistance);
    }

    @Transactional
    @Test
    public void testShouldRetrieveTraining() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User user = userService.createUser(createUserData);
        Training entity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training createdTraining = trainingService.createTraining(entity);
        // when
        Optional<Training> retrievedTraining = trainingService.getTraining(entity.getId());
        // then
        Assertions.assertThat(retrievedTraining.isPresent()).isTrue();
        Assertions.assertThat(createdTraining).usingRecursiveComparison().isEqualTo(retrievedTraining.get());
    }

    @Transactional
    @Test
    public void testShouldRetrieveTrainingsOfType() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User user = userService.createUser(createUserData);
        Training trainingCyclicEntity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training trainingWalkingEntity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.WALKING,
                12.4,
                7.2
        );
        trainingService.createTraining(trainingCyclicEntity);
        trainingService.createTraining(trainingWalkingEntity);
        // when
        List<Training> retrievedTrainings = trainingService.findAllTrainingsOfType(ActivityType.CYCLING);
        // then
        Assertions.assertThat(retrievedTrainings.size()).isGreaterThan(0);
        retrievedTrainings.forEach(training -> Assertions.assertThat(training.getActivityType() == ActivityType.CYCLING));
    }

    @Transactional
    @Test
    public void testShouldRetrieveUserTrainings() {
        // given
        User createUserData1 = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User createUserData2 = new User(
                "tes1",
                "tes1",
                LocalDate.now(),
                "test@test1.com"
        );
        User user1 = userService.createUser(createUserData1);
        User user2 = userService.createUser(createUserData2);
        Training trainingUser1Entity = new Training(
                user1,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training trainingUser2Entity = new Training(
                user2,
                new Date(),
                new Date(),
                ActivityType.WALKING,
                12.4,
                7.2
        );
        trainingService.createTraining(trainingUser1Entity);
        trainingService.createTraining(trainingUser2Entity);
        // when
        List<Training> retrievedTrainings = trainingService.findAllUserTrainings(user1.getId());
        // then
        Assertions.assertThat(retrievedTrainings.size()).isGreaterThan(0);
        retrievedTrainings.forEach(training -> Assertions.assertThat(Objects.equals(training.getUser().getId(), user1.getId())));
    }

    @Transactional
    @Test
    public void testShouldRetrieveTrainingsEndedAfter() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User user = userService.createUser(createUserData);
        Training trainingEntity1 = new Training(
                user,
                new Date(),
                new Date(2018 + 1900, Calendar.JUNE, 5),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training trainingEntity2 = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.WALKING,
                12.4,
                7.2
        );
        trainingService.createTraining(trainingEntity1);
        trainingService.createTraining(trainingEntity2);
        // when
        List<Training> retrievedTrainings = trainingService.findAllTrainingsEndedAfter(new Date(2016 + 1900, Calendar.JUNE, 5));
        // then
        Assertions.assertThat(retrievedTrainings.size()).isGreaterThan(0);
    }

    @Transactional
    @Test
    public void testShouldRetrieveAllTrainings() {
        // given
        User createUserData = new User(
                "test",
                "test",
                LocalDate.now(),
                "test@test.com"
        );
        User user = userService.createUser(createUserData);
        Training trainingCyclicEntity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.CYCLING,
                12.4,
                7.2
        );
        Training trainingWalkingEntity = new Training(
                user,
                new Date(),
                new Date(),
                ActivityType.WALKING,
                12.4,
                7.2
        );
        trainingService.createTraining(trainingCyclicEntity);
        trainingService.createTraining(trainingWalkingEntity);
        // when
        List<Training> retrievedTrainings = trainingService.findAllTrainings();
        // then
        Assertions.assertThat(retrievedTrainings.size()).isEqualTo(2);
    }
}
