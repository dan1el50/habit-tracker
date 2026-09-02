package com.daniel.habit_tracker.repository;

import com.daniel.habit_tracker.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserId(Long userId);
    Optional<Habit> findByIdAndUserId(Long id, Long userId);
}