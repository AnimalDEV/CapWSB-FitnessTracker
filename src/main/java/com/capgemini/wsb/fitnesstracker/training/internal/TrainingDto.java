package com.capgemini.wsb.fitnesstracker.training.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrainingDto {
    private Long Id;
    private Long userId;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private Double distance;
    private Double averageSpeed;

    public TrainingDto(
            @Nullable Long Id,
            @Nullable Long userId,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime,
            ActivityType activityType,
            @Nullable Double distance,
            @Nullable Double averageSpeed
    ) {
        this.Id = Id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}
