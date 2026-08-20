package com.daniel.habit_tracker.repository;

import com.daniel.habit_tracker.entity.HabitEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitEntryRepository extends JpaRepository<HabitEntry, Long> {

    Optional<HabitEntry> findByHabitIdAndCompletedDate(Long habitId, LocalDate completedDate);
    List<HabitEntry> findByHabitIdOrderByCompletedDateDesc(Long habitId);
}
