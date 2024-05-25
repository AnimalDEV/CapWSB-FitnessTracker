package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TrainingUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;

    public TrainingUserDto(
            Long id,
            @Nullable String firstName,
            @Nullable String lastName,
            @Nullable String email,
            @Nullable @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDate birthDate
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
    }
}
