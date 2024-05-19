package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endedAfter,
            @RequestParam(required = false) ActivityType activity
    ) {
        List<Training> trainings;

        if(userId != null) {
            trainings = trainingService.findAllUserTrainings(userId);
        } else if(endedAfter != null) {
            trainings = trainingService.findAllTrainingsEndedAfter(endedAfter);
        } else if(activity != null) {
            trainings = trainingService.findAllTrainingsOfType(activity);
        } else {
            trainings = trainingService.findAllTrainings();
        }

        return trainings
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping( "/{trainingId}")
    public TrainingDto getTraining(@PathVariable Long trainingId) {
        Optional<Training> training = trainingService.getTraining(trainingId);

        return training.map(this.trainingMapper::toDto).orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

    @PostMapping
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) {
        final Training mappedTraining = this.trainingMapper.toEntity(trainingDto);
        final Training createdTraining = this.trainingService.createTraining(mappedTraining);

        return this.trainingMapper.toDto(createdTraining);
    }

    @PostMapping( "/{trainingId}/set-distance")
    public TrainingDto updateTrainingDistance(@PathVariable Long trainingId, @RequestBody Double distance) {
        Optional<Training> updatedTraining = this.trainingService.updateTrainingDistance(trainingId, distance);
        return updatedTraining.map(this.trainingMapper::toDto).orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

}