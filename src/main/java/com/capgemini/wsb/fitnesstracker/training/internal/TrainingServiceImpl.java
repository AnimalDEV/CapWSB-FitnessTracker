package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return this.trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> findAllUserTrainings(Long userId) {
        return this.trainingRepository.findByUser(userId);
    }

    @Override
    public List<Training> findAllTrainingsOfType(ActivityType activityType) {
        return this.trainingRepository.findByType(activityType);
    }

    @Override
    public List<Training> findAllTrainingsEndedAfter(Date endedAfter) {
        return this.trainingRepository.findEndedAfter(endedAfter);
    }

    @Override
    public List<Training> findAllTrainings() {
        return this.trainingRepository.findAll();
    }

    @Override
    public Training createTraining(Training training) {
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, creation is not permitted!");
        }
        return trainingRepository.save(training);
    }

    @Override
    public Optional<Training> updateTrainingDistance(Long trainingId, Double distance) {
        return trainingRepository.updateTrainingDistance(trainingId, distance);
    }
}
