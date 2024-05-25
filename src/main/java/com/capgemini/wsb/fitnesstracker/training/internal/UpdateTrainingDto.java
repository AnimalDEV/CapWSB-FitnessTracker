package com.capgemini.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateTrainingDto {
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private Double distance;
    private Double averageSpeed;

    public UpdateTrainingDto(
            @Nullable Long Id,
            @Nullable TrainingUserDto user,
            @Nullable Long userId,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime,
            ActivityType activityType,
            @Nullable Double distance,
            @Nullable Double averageSpeed
    ) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}
