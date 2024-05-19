package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.springframework.stereotype.Component;

@Component
class TrainingMapper {

    private final UserServiceImpl userServiceImpl;

    public TrainingMapper(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
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
}
