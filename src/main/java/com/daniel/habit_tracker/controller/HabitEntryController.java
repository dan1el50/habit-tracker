package com.daniel.habit_tracker.controller;

import com.daniel.habit_tracker.dto.CreateHabitEntryRequest;
import com.daniel.habit_tracker.entity.HabitEntry;
import com.daniel.habit_tracker.service.HabitEntryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits/{habitId}/entries")
public class HabitEntryController {

    private final HabitEntryService habitEntryService;

    public HabitEntryController(HabitEntryService habitEntryService){
        this.habitEntryService = habitEntryService;
    }

    @PostMapping
    public HabitEntry createHabitEntry(@Valid @RequestBody CreateHabitEntryRequest request, @PathVariable Long habitId){
        return habitEntryService.createHabitEntry(request, habitId);
    }

    @GetMapping
    public List<HabitEntry> getHabitEntries(@PathVariable Long habitId){
        return habitEntryService.getHabitEntries(habitId);
    }
}
