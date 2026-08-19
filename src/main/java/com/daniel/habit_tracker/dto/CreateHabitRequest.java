package com.daniel.habit_tracker.dto;

import com.daniel.habit_tracker.entity.Frequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHabitRequest {

    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Frequency frequency;
}
