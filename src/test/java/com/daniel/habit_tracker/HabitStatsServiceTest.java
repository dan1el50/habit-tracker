package com.daniel.habit_tracker;

import com.daniel.habit_tracker.dto.HabitStatsResponse;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.entity.Frequency;
import com.daniel.habit_tracker.entity.HabitEntry;
import com.daniel.habit_tracker.service.HabitEntryService;
import com.daniel.habit_tracker.service.HabitService;
import com.daniel.habit_tracker.service.HabitStatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitStatsServiceTest {

    @Mock
    private HabitEntryService habitEntryService;

    @Mock
    private HabitService habitService;

    @InjectMocks
    private HabitStatsService habitStatsService;

    @Test
    void getHabitStats_noEntries_returnsZeroStreaks() {
        // Arrange
        Long habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Read");
        fakeHabit.setDescription("Read for 20 minutes");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(Collections.emptyList());

        // Act
        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        // Assert
        assertThat(result.getCurrentStreak()).isEqualTo(0);
        assertThat(result.getLongestStreak()).isEqualTo(0);
        assertThat(result.getName()).isEqualTo("Read");
    }

    @Test
    void getHabitStats_singleEntryToday_returnsStreakOne() {
        Long habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Workout");
        fakeHabit.setDescription("Do a workout at least 4 times a week");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());
        List<HabitEntry> entries = List.of(todayEntry);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getCurrentStreak()).isEqualTo(1);
        assertThat(result.getLongestStreak()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Workout");
    }

    @Test
    void getHabitStats_unbrokenStreakOfThree_returnsStreakThree() {
        Long habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Walk 10k steps");
        fakeHabit.setDescription("Do a workout at least 4 times a week");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry yesterdayEntry = new HabitEntry();
        yesterdayEntry.setCompletedDate(LocalDate.now().minusDays(1));

        HabitEntry twoDaysAgoEntry = new HabitEntry();
        twoDaysAgoEntry.setCompletedDate(LocalDate.now().minusDays(2));

        List<HabitEntry> entries = List.of(todayEntry, yesterdayEntry, twoDaysAgoEntry);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getName()).isEqualTo("Walk 10k steps");
        assertThat(result.getCurrentStreak()).isEqualTo(3);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }

    @Test
    void getHabitStats_forgivenDayMiss_returnsStreakThree() {
        Long habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Walk 10k steps");
        fakeHabit.setDescription("Do a workout at least 4 times a week");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry yesterdayEntry = new HabitEntry();
        yesterdayEntry.setCompletedDate(LocalDate.now().minusDays(2));

        HabitEntry twoDaysAgoEntry = new HabitEntry();
        twoDaysAgoEntry.setCompletedDate(LocalDate.now().minusDays(3));

        List<HabitEntry> entries = List.of(todayEntry, yesterdayEntry, twoDaysAgoEntry);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getName()).isEqualTo("Walk 10k steps");
        assertThat(result.getCurrentStreak()).isEqualTo(3);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }

    @Test
    void getHabitStats_brokenStreak_returnsCurrentStreakOfOneAndLongestStreakOfThree() {
        Long habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Walk 10k steps");
        fakeHabit.setDescription("Do a workout at least 4 times a week");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry previousEntry = new HabitEntry();
        previousEntry.setCompletedDate(LocalDate.now().minusDays(3));

        HabitEntry dayBeforePreviousEntry = new HabitEntry();
        dayBeforePreviousEntry.setCompletedDate(LocalDate.now().minusDays(4));

        HabitEntry twoDaysBeforePreviousEntry = new HabitEntry();
        twoDaysBeforePreviousEntry.setCompletedDate(LocalDate.now().minusDays(5));

        List<HabitEntry> entries = List.of(todayEntry, previousEntry, dayBeforePreviousEntry, twoDaysBeforePreviousEntry);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getName()).isEqualTo("Walk 10k steps");
        assertThat(result.getCurrentStreak()).isEqualTo(1);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }
}