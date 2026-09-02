package com.daniel.habit_tracker.service;

import com.daniel.habit_tracker.dto.HabitStatsResponse;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.entity.HabitEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class HabitStatsService {

    private final HabitEntryService habitEntryService;
    private final HabitService habitService;

    public HabitStatsService(HabitEntryService habitEntryService, HabitService habitService) {
        this.habitEntryService = habitEntryService;
        this.habitService = habitService;
    }

    private int getCurrentStreak(Long habitId, Long userId) {
        List<HabitEntry> habitEntryHistory = habitEntryService.getHabitEntries(habitId, userId);
        if (habitEntryHistory.isEmpty()) {
            return 0;
        }
        if (ChronoUnit.DAYS.between(habitEntryHistory.getFirst().getCompletedDate(), LocalDate.now()) >= 3) {
            return 0;
        }

        int count = 1;
        LocalDate previousDate = habitEntryHistory.getFirst().getCompletedDate();

        for (int i = 1; i < habitEntryHistory.size(); i++) {
            if (ChronoUnit.DAYS.between(habitEntryHistory.get(i).getCompletedDate(), previousDate) <= 2) {
                count++;
                previousDate = habitEntryHistory.get(i).getCompletedDate();
            } else {
                break;
            }
        }
        return count;
    }

    private int getLongestStreak(Long habitId, Long userId) {
        int runningStreak = 1;
        int maxStreak = 0;

        List<HabitEntry> habitEntryHistory = habitEntryService.getHabitEntries(habitId, userId);

        if (habitEntryHistory.isEmpty()) {
            return 0;
        } else {
            for (int i = 0; i < habitEntryHistory.size() - 1; i++) {
                LocalDate current = habitEntryHistory.get(i).getCompletedDate();
                LocalDate previousEntry = habitEntryHistory.get(i + 1).getCompletedDate();
                if (!(ChronoUnit.DAYS.between(previousEntry, current) > 2)) {
                    runningStreak++;
                } else {
                    maxStreak = Math.max(runningStreak, maxStreak);
                    runningStreak = 1;
                }
            }
            maxStreak = Math.max(runningStreak, maxStreak);
        }
        return maxStreak;
    }

    public HabitStatsResponse getHabitStats(Long habitId, Long userId) {
        Habit habit = habitService.getHabitById(habitId, userId);
        return new HabitStatsResponse(habit.getName(), habit.getCreatedAt(),
                getCurrentStreak(habitId, userId), getLongestStreak(habitId, userId));
    }
}