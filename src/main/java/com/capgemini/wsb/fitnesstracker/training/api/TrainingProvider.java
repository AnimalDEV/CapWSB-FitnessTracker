package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on ID.
     * If the training with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located Training, or {@link Optional#empty()} if not found
     */
    Optional<Training> getTraining(Long trainingId);

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
}
