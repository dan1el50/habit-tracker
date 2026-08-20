package com.daniel.habit_tracker.controller;

import com.daniel.habit_tracker.dto.HabitStatsResponse;
import com.daniel.habit_tracker.service.HabitStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/habits/{habitId}/stats")
public class HabitStatsController {

    private final HabitStatsService habitStatsService;

    public HabitStatsController(HabitStatsService habitStatsService){
        this.habitStatsService = habitStatsService;
    }

    @GetMapping
    public HabitStatsResponse getHabitStats(@PathVariable Long habitId){
        return habitStatsService.getHabitStats(habitId);
    }
}
