//package com.daniel.habit_tracker;
//
//import com.daniel.habit_tracker.dto.CreateHabitRequest;
//import com.daniel.habit_tracker.entity.Frequency;
//import com.daniel.habit_tracker.entity.Habit;
//import com.daniel.habit_tracker.exceptions.HabitNotFoundException;
//import com.daniel.habit_tracker.repository.HabitRepository;
//import com.daniel.habit_tracker.service.HabitService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class HabitServiceTest {
//
//    @Mock
//    private HabitRepository habitRepository;
//
//    @InjectMocks
//    private HabitService habitService;
//
//    @Test
//    void getHabitById_habitExists_returnHabit() {
//        Long habitId = 1L;
//        Habit fakeHabit = new Habit();
//        fakeHabit.setId(habitId);
//        fakeHabit.setName("Read");
//        fakeHabit.setDescription("Read 10 pages every evening before sleep.");
//        fakeHabit.setFrequency(Frequency.DAILY);
//        fakeHabit.setCreatedAt(LocalDateTime.now());
//
//        when(habitRepository.findById(habitId)).thenReturn(Optional.of(fakeHabit));
//
//        Habit result = habitService.getHabitById(habitId);
//        assertThat(result.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    void getHabitById_nonexistingHabit_throwsHabitNotFoundException() {
//        Long habitId = 1L;
//        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> habitService.getHabitById(habitId)).isInstanceOf(HabitNotFoundException.class);
//    }
//
//    @Test
//    void getAllHabits_returnListOfHabits() {
//        Long habitId1 = 1L;
//        Long habitId2 = 2L;
//
//        Habit fakeHabit1 = new Habit();
//        fakeHabit1.setId(habitId1);
//        fakeHabit1.setName("Read");
//        fakeHabit1.setFrequency(Frequency.DAILY);
//        fakeHabit1.setCreatedAt(LocalDateTime.now());
//
//        Habit fakeHabit2 = new Habit();
//        fakeHabit2.setId(habitId2);
//        fakeHabit2.setName("Walk");
//        fakeHabit2.setFrequency(Frequency.DAILY);
//        fakeHabit2.setCreatedAt(LocalDateTime.now());
//
//        when(habitRepository.findAll()).thenReturn(List.of(fakeHabit1, fakeHabit2));
//
//        List<Habit> result = habitService.getAllHabits();
//
//        assertThat(result).hasSize(2);
//        //Another variant
//        // assertThat(result).containsExactly(fakeHabit1, fakeHabit2);
//    }
//
//    @Test
//    void createHabit_validHabit_saveHabit() {
//        // 1. Build the input DTO
//        CreateHabitRequest createHabitRequest = new CreateHabitRequest();
//        createHabitRequest.setName("Walk");
//        createHabitRequest.setFrequency(Frequency.DAILY);
//
//        // 2. Stub save() so the service has something to return
//        Habit fakeSavedHabit = new Habit();
//        fakeSavedHabit.setId(1L);
//        fakeSavedHabit.setName("Walk");
//        fakeSavedHabit.setFrequency(Frequency.DAILY);
//        when(habitRepository.save(any(Habit.class))).thenReturn(fakeSavedHabit);
//
//        // 3. Call the real method under test
//        Habit result = habitService.createHabit(createHabitRequest);
//
//        // 4. Capture the Habit object that was actually passed into save()
//        ArgumentCaptor<Habit> habitCaptor = ArgumentCaptor.forClass(Habit.class);
//        verify(habitRepository).save(habitCaptor.capture());
//        Habit capturedHabit = habitCaptor.getValue();
//
//        // 5. Assert the captured Habit was built correctly from the request
//        assertThat(capturedHabit.getName()).isEqualTo("Walk");
//        assertThat(capturedHabit.getFrequency()).isEqualTo(Frequency.DAILY);
//
//        // 6. Assert the method returns what save() gave back
//        assertThat(result.getId()).isEqualTo(1L);
//    }
//
//    @Test
//    void deleteHabit_callsRepositoryDeleteById() {
//        Long habitId = 1L;
//
//        habitService.deleteHabit(habitId);
//
//        verify(habitRepository).deleteById(habitId);
//    }
//}