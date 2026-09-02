//package com.daniel.habit_tracker.controller;
//
//import com.daniel.habit_tracker.entity.Frequency;
//import com.daniel.habit_tracker.entity.Habit;
//import com.daniel.habit_tracker.repository.HabitRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@Testcontainers
//@Transactional
//class HabitControllerIntegrationTest {
//
//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private HabitRepository habitRepository;
//
//    @Test
//    void createHabit_validRequest_persistsAndReturnsHabit() throws Exception {
//        String requestBody = """
//            {
//              "name": "Read",
//              "description": "Read 10 pages every evening",
//              "frequency": "DAILY"
//            }
//            """;
//
//        mockMvc.perform(post("/api/habits")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Read"))
//                .andExpect(jsonPath("$.frequency").value("DAILY"))
//                .andExpect(jsonPath("$.id").exists());
//    }
//
//    @Test
//    void getAllHabits_habitsExist_returnsListOfHabits() throws Exception{
//        Habit habit = new Habit();
//        habit.setName("Read");
//        habit.setDescription("Read 10 pages every evening");
//        habit.setFrequency(Frequency.DAILY);
//        habitRepository.save(habit);
//
//        mockMvc.perform(get("/api/habits"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].name").value("Read"));
//    }
//
//    @Test
//    void getHabitById_idExists_returnHabit() throws Exception {
//        Habit habit = new Habit();
//        habit.setName("Read");
//        habit.setDescription("Read 10 pages every evening");
//        habit.setFrequency(Frequency.DAILY);
//        Habit savedHabit = habitRepository.save(habit);
//
//        mockMvc.perform(get("/api/habits/" + savedHabit.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Read"));
//    }
//
//    @Test
//    void getHabitById_idDoesNotExist_returnError() throws Exception {
//        mockMvc.perform(get("/api/habits/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.message").value("Habit not found with id: 1"));
//    }
//
//    @Test
//    void deleteHabit_idExists_returnOk() throws Exception{
//        Habit habit = new Habit();
//        habit.setName("Read");
//        habit.setDescription("Read 10 pages every evening");
//        habit.setFrequency(Frequency.DAILY);
//        Habit savedHabit = habitRepository.save(habit);
//
//        mockMvc.perform(delete("/api/habits/" + savedHabit.getId()))
//                .andExpect(status().isOk());
//
//        assertThat(habitRepository.findById(savedHabit.getId())).isEmpty();
//
//    }
//
//}