package com.daniel.habit_tracker.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitEntryRequest {

    private LocalDate date;

}
