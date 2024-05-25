package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getAllTrainingsByActivityType(
            @RequestParam() ActivityType activityType
    ) {
        return trainingService.findAllTrainingsOfType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getAllTrainingsFinishedAfter(
            @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date afterTime
    ) {
        return trainingService.findAllTrainingsEndedAfter(afterTime)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> getAllUserTrainings(
            @PathVariable Long userId
    ) {
        return trainingService.findAllUserTrainings(userId)
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto addTraining(@RequestBody TrainingDto trainingDto) {
        final Training mappedTraining = this.trainingMapper.toEntity(trainingDto);
        final Training createdTraining = this.trainingService.createTraining(mappedTraining);

        return this.trainingMapper.toDto(createdTraining);
    }

    @PutMapping( "/{trainingId}")
    public TrainingDto updateTraining(@PathVariable Long trainingId, @RequestBody UpdateTrainingDto trainingDto) {
        final Training mappedTraining = this.trainingMapper.toEntity(trainingId, trainingDto);
        final Optional<Training> updatedTraining = this.trainingService.updateTraining(trainingId, mappedTraining);

        return updatedTraining.map(this.trainingMapper::toDto).orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }

}