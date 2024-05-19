package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Query searching users by user.
     *
     * @param userId userId to be filtered by
     * @return {@link List<Training>} containing found trainings
     */
    default List<Training> findByUser(Long userId) {
        return findAll().stream()
                .filter(user -> user.getId().longValue() == userId).toList();
    }

    /**
     * Query searching users by email address.
     *
     * @param activityType {@link ActivityType} activity type to be filtered by
     * @return {@link List<Training>} containing found trainings
     */
    default List<Training> findByType(ActivityType activityType) {
        return findAll().stream()
                .filter(user -> user.getActivityType() == activityType).toList();
    }

    /**
     * Query searching users by email address.
     *
     * @param endedAfter {@link Date} to be filtered by
     * @return {@link List<Training>} containing found trainings
     */
    default List<Training> findEndedAfter(Date endedAfter) {
        return findAll().stream()
                .filter(user -> user.getEndTime().toInstant().isAfter(endedAfter.toInstant())).toList();
    }

    /**
     * Update training distance by id.
     *
     * @param id id of the training to update
     * @param distance value to set
     * @return {@link Optional} containing updated training or {@link Optional#empty()} if training was not found
     */
    default Optional<Training> updateTrainingDistance(Long id, Double distance) {
        Optional<Training> training = findById(id);

        if(training.isEmpty()) {
            return training;
        }

        training.get().setDistance(distance);

        return Optional.of(this.saveAndFlush(training.get()));
    }
}
