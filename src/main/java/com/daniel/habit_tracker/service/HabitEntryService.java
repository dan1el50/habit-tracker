package com.daniel.habit_tracker.service;

import com.daniel.habit_tracker.dto.CreateHabitEntryRequest;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.entity.HabitEntry;
import com.daniel.habit_tracker.exceptions.HabitAlreadyCompletedException;
import com.daniel.habit_tracker.repository.HabitEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitEntryService {

    private final HabitEntryRepository habitEntryRepository;
    private final HabitService habitService;

    public HabitEntryService(HabitEntryRepository habitEntryRepository, HabitService habitService){
        this.habitEntryRepository = habitEntryRepository;
        this.habitService = habitService;
    }

    public HabitEntry createHabitEntry(CreateHabitEntryRequest request, Long habitId, Long userId){
        Habit habit = habitService.getHabitById(habitId, userId);
        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();

        if (habitEntryRepository.findByHabitIdAndCompletedDate(habit.getId(), date).isPresent()){
            throw new HabitAlreadyCompletedException("Habit already marked as done for " + date);
        }

        HabitEntry habitEntry = new HabitEntry();
        habitEntry.setHabit(habit);
        habitEntry.setCompletedDate(date);
        return habitEntryRepository.save(habitEntry);
    }

    public List<HabitEntry> getHabitEntries(Long habitId, Long userId){
        habitService.getHabitById(habitId, userId); // ownership check, throws if not found/not owned
        return habitEntryRepository.findByHabitIdOrderByCompletedDateDesc(habitId);
    }
}