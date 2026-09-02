package com.daniel.habit_tracker.controller;

import com.daniel.habit_tracker.dto.CreateHabitRequest;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.security.UserPrincipal;
import com.daniel.habit_tracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Habit createHabit(@Valid @RequestBody CreateHabitRequest request,
                             @AuthenticationPrincipal UserPrincipal principal) {
        return habitService.createHabit(request, principal.getUser());
    }

    @GetMapping
    public List<Habit> getAllHabits(@AuthenticationPrincipal UserPrincipal principal) {
        return habitService.getAllHabits(principal.getUser().getId());
    }

    @GetMapping("/{id}")
    public Habit getHabitById(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        return habitService.getHabitById(id, principal.getUser().getId());
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        habitService.deleteHabit(id, principal.getUser().getId());
    }
}