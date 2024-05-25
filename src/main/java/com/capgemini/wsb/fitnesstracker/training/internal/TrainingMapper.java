package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class TrainingMapper {

    private static final Logger log = LoggerFactory.getLogger(TrainingMapper.class);
    private final UserServiceImpl userServiceImpl;
    private final TrainingServiceImpl trainingServiceImpl;

    public TrainingMapper(UserServiceImpl userServiceImpl, TrainingServiceImpl trainingServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.trainingServiceImpl = trainingServiceImpl;
    }

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                this.mapUserToTrainingUserDto(training.getUser()),
                training.getUser().getId(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    Training toEntity(TrainingDto trainingDto) throws UserNotFoundException {
        User user = this.userServiceImpl.getUser(
                trainingDto.getUserId()).orElseThrow(() -> new UserNotFoundException(trainingDto.getUserId())
        );

        return new Training(
                user,
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    Training toEntity(Long trainingId, UpdateTrainingDto trainingDto) throws UserNotFoundException {
        Training originalTraining = this.trainingServiceImpl.getTraining(trainingId).orElseThrow(() -> new TrainingNotFoundException(trainingId));

        return new Training(
                originalTraining.getUser(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    private TrainingUserDto mapUserToTrainingUserDto(User user) {
        return new TrainingUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthdate()
        );
    }
}
