package com.daniel.habit_tracker;

import com.daniel.habit_tracker.dto.CreateHabitEntryRequest;
import com.daniel.habit_tracker.entity.Frequency;
import com.daniel.habit_tracker.entity.Habit;
import com.daniel.habit_tracker.entity.HabitEntry;
import com.daniel.habit_tracker.exceptions.HabitAlreadyCompletedException;
import com.daniel.habit_tracker.repository.HabitEntryRepository;
import com.daniel.habit_tracker.service.HabitEntryService;
import com.daniel.habit_tracker.service.HabitService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class HabitEntryServiceTests {

    @Mock
    private HabitEntryRepository habitEntryRepository;

    @Mock
    private HabitService habitService;

    @InjectMocks
    private HabitEntryService habitEntryService;

    private LocalDate today;
    private Long habitId;
    private Habit fakeHabit;

    @BeforeEach
    void setUp() {
        today = LocalDate.now();
        habitId = 1L;
        fakeHabit = new Habit();
        fakeHabit.setId(habitId);
        fakeHabit.setName("Read");
        fakeHabit.setFrequency(Frequency.DAILY);
        fakeHabit.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createHabitEntry_dateProvided_returnNewHabitEntry() {
        Long habitEntryId = 1L;
        HabitEntry fakeHabitEntry = new HabitEntry();
        fakeHabitEntry.setId(habitEntryId);
        fakeHabitEntry.setCompletedDate(today);
        fakeHabitEntry.setHabit(fakeHabit);

        CreateHabitEntryRequest createHabitEntryRequest = new CreateHabitEntryRequest();
        createHabitEntryRequest.setDate(today);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryRepository.findByHabitIdAndCompletedDate(habitId, today)).thenReturn(Optional.empty());
        when(habitEntryRepository.save(any(HabitEntry.class))).thenReturn(fakeHabitEntry);

        HabitEntry result = habitEntryService.createHabitEntry(createHabitEntryRequest, habitId);

        ArgumentCaptor<HabitEntry> habitEntryCaptor = ArgumentCaptor.forClass(HabitEntry.class);
        verify(habitEntryRepository).save(habitEntryCaptor.capture());
        HabitEntry capturedHabitEntry =  habitEntryCaptor.getValue();

        assertThat(capturedHabitEntry.getCompletedDate()).isEqualTo(today);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createHabitEntry_dateNotProvided_defaultsToToday() {

        Long habitEntryId = 1L;
        HabitEntry fakeHabitEntry = new HabitEntry();
        fakeHabitEntry.setId(habitEntryId);
        fakeHabitEntry.setCompletedDate(today);
        fakeHabitEntry.setHabit(fakeHabit);

        CreateHabitEntryRequest createHabitEntryRequest = new CreateHabitEntryRequest();

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryRepository.findByHabitIdAndCompletedDate(habitId, today)).thenReturn(Optional.empty());
        when(habitEntryRepository.save(any(HabitEntry.class))).thenReturn(fakeHabitEntry);

        HabitEntry result = habitEntryService.createHabitEntry(createHabitEntryRequest, habitId);

        ArgumentCaptor<HabitEntry> habitEntryCaptor = ArgumentCaptor.forClass(HabitEntry.class);
        verify(habitEntryRepository).save(habitEntryCaptor.capture());
        HabitEntry capturedHabitEntry =  habitEntryCaptor.getValue();

        assertThat(capturedHabitEntry.getCompletedDate()).isEqualTo(today);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createHabitEntry_repeatedDate_throwsError() {

        Long habitEntryId = 1L;
        HabitEntry fakeHabitEntry = new HabitEntry();
        fakeHabitEntry.setId(habitEntryId);
        fakeHabitEntry.setCompletedDate(today);
        fakeHabitEntry.setHabit(fakeHabit);

        CreateHabitEntryRequest createHabitEntryRequest = new CreateHabitEntryRequest();
        createHabitEntryRequest.setDate(today);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryRepository.findByHabitIdAndCompletedDate(habitId, today)).thenReturn(Optional.of(fakeHabitEntry));

        assertThatThrownBy(() -> habitEntryService.createHabitEntry(createHabitEntryRequest, habitId)).isInstanceOf(HabitAlreadyCompletedException.class);
    }

    @Test
    void getHabitEntries_returnListOfEntries() {

        Long habitEntryId1 = 1L;
        HabitEntry fakeHabitEntry1 = new HabitEntry();
        fakeHabitEntry1.setId(habitEntryId1);
        fakeHabitEntry1.setCompletedDate(LocalDate.now());
        fakeHabitEntry1.setHabit(fakeHabit);

        Long habitEntryId2 = 2L;
        HabitEntry fakeHabitEntry2 = new HabitEntry();
        fakeHabitEntry2.setId(habitEntryId2);
        fakeHabitEntry2.setCompletedDate(LocalDate.now().minusDays(1));
        fakeHabitEntry2.setHabit(fakeHabit);

        when(habitService.getHabitById(habitId)).thenReturn(fakeHabit);
        when(habitEntryRepository.findByHabitIdOrderByCompletedDateDesc(habitId)).thenReturn(List.of(fakeHabitEntry1, fakeHabitEntry2));

        List<HabitEntry> result = habitEntryService.getHabitEntries(habitId);

        assertThat(result).containsExactly(fakeHabitEntry1, fakeHabitEntry2);
    }
}
