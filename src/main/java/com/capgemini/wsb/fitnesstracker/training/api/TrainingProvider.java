package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {
    /**
     * Retrieves all trainings of provided user.
     *
     * @param userId {@link Long} id of the user
     * @return An {@link Optional} containing the trainings,
     */
    List<Training> findAllUserTrainings(Long userId);

    /**
     * Retrieves all trainings of provided type.
     *
     * @param activityType {@link ActivityType} type of trainings
     * @return An {@link Optional} containing the trainings,
     */
    List<Training> findAllTrainingsOfType(ActivityType activityType);

    /**
     * Retrieves all trainings ended after Date.
     *
     * @param endedAfter {@link Date} date to get trainings after
     * @return An {@link Optional} containing the trainings,
     */
    List<Training> findAllTrainingsEndedAfter(Date endedAfter);
    /**
     * Retrieves all trainings.
     *
     * @return An {@link Optional} containing the all trainings,
     */
    List<Training> findAllTrainings();

    /**
     * Retrieves training by id.
     *
     * @param trainingId {@link Long} id of the training
     * @return An {@link Optional} containing the all trainings,
     */
    Optional<Training> getTraining(Long trainingId);
}
