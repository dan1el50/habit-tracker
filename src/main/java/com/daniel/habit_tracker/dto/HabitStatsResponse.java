package com.daniel.habit_tracker.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class HabitStatsResponse {

    private String name;
    private LocalDateTime createdAt;
    private int currentStreak;
    private int longestStreak;

}
