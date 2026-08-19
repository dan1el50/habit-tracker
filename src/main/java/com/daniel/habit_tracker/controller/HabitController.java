package com.daniel.habit_tracker.controller;

import com.daniel.habit_tracker.dto.CreateHabitRequest;
import com.daniel.habit_tracker.entity.Frequency;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public Habit createHabit(@Valid @RequestBody CreateHabitRequest request){
        return habitService.createHabit(request);
    }

    @GetMapping
    public List<Habit> getAllHabits(){
        return habitService.getAllHabits();
    }

    @GetMapping("/{id}")
    public Habit getHabitById(@PathVariable Long id){
        return habitService.getHabitById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id){
        habitService.deleteHabit(id);
    }
}
