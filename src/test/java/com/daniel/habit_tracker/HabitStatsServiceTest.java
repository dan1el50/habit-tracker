package com.daniel.habit_tracker;

import com.daniel.habit_tracker.dto.HabitStatsResponse;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.entity.Frequency;
import com.daniel.habit_tracker.entity.HabitEntry;
import com.daniel.habit_tracker.service.HabitEntryService;
import com.daniel.habit_tracker.service.HabitService;
import com.daniel.habit_tracker.service.HabitStatsService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitStatsServiceTest {

    @Mock
    private HabitEntryService habitEntryService;

    @Mock
    private HabitService habitService;

    @InjectMocks
    private HabitStatsService habitStatsService;

    private Long habitId;

    @BeforeEach
    void setUp(){
        habitId = 1L;
        Habit fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Read");
        fakeHabit.setDescription("Read 10 pages every evening before sleep.");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
    }

    @Test
    void getHabitStats_noEntries_returnsZeroStreaks() {
        when(habitEntryService.getHabitEntries(habitId)).thenReturn(Collections.emptyList());

        // Act
        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        // Assert
        assertThat(result.getCurrentStreak()).isEqualTo(0);
        assertThat(result.getLongestStreak()).isEqualTo(0);
    }

    @Test
    void getHabitStats_singleEntryToday_returnsStreakOne() {
        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());
        List<HabitEntry> entries = List.of(todayEntry);

        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getCurrentStreak()).isEqualTo(1);
        assertThat(result.getLongestStreak()).isEqualTo(1);
    }

    @Test
    void getHabitStats_unbrokenStreakOfThree_returnsStreakThree() {
        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry yesterdayEntry = new HabitEntry();
        yesterdayEntry.setCompletedDate(LocalDate.now().minusDays(1));

        HabitEntry twoDaysAgoEntry = new HabitEntry();
        twoDaysAgoEntry.setCompletedDate(LocalDate.now().minusDays(2));

        List<HabitEntry> entries = List.of(todayEntry, yesterdayEntry, twoDaysAgoEntry);

        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getCurrentStreak()).isEqualTo(3);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }

    @Test
    void getHabitStats_forgivenDayMiss_returnsStreakThree() {
        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry minus2DaysEntry = new HabitEntry();
        minus2DaysEntry.setCompletedDate(LocalDate.now().minusDays(2));

        HabitEntry minus3DaysEntry = new HabitEntry();
        minus3DaysEntry.setCompletedDate(LocalDate.now().minusDays(3));

        List<HabitEntry> entries = List.of(todayEntry, minus2DaysEntry, minus3DaysEntry);

        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getCurrentStreak()).isEqualTo(3);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }

    @Test
    void getHabitStats_brokenStreak_returnsCurrentStreakOfOneAndLongestStreakOfThree() {
        HabitEntry todayEntry = new HabitEntry();
        todayEntry.setCompletedDate(LocalDate.now());

        HabitEntry minus3DaysEntry = new HabitEntry();
        minus3DaysEntry.setCompletedDate(LocalDate.now().minusDays(3));

        HabitEntry minus4DaysEntry = new HabitEntry();
        minus4DaysEntry.setCompletedDate(LocalDate.now().minusDays(4));

        HabitEntry minus5DaysEntry = new HabitEntry();
        minus5DaysEntry.setCompletedDate(LocalDate.now().minusDays(5));

        List<HabitEntry> entries = List.of(todayEntry, minus3DaysEntry, minus4DaysEntry, minus5DaysEntry);

        when(habitEntryService.getHabitEntries(habitId)).thenReturn(entries);

        HabitStatsResponse result = habitStatsService.getHabitStats(habitId);

        assertThat(result.getCurrentStreak()).isEqualTo(1);
        assertThat(result.getLongestStreak()).isEqualTo(3);
    }
}