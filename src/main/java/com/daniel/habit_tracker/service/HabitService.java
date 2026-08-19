package com.daniel.habit_tracker.service;

import com.daniel.habit_tracker.dto.CreateHabitRequest;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.exceptions.HabitNotFoundException;
import com.daniel.habit_tracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService (HabitRepository habitRepository){
        this.habitRepository = habitRepository;
    }

    public Habit createHabit(CreateHabitRequest request){
        Habit habit = new Habit();
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency());
        return habitRepository.save(habit);
    }

    public List<Habit> getAllHabits(){
        return habitRepository.findAll();
    }

    public Habit getHabitById(Long id){
        return habitRepository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException("Habit not found with id: " + id));
    }

    public void deleteHabit(Long id){
        habitRepository.deleteById(id);
    }
}
