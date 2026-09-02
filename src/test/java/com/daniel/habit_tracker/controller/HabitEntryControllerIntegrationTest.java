//package com.daniel.habit_tracker.controller;
//
//import com.daniel.habit_tracker.entity.Frequency;
//import com.daniel.habit_tracker.entity.Habit;
//import com.daniel.habit_tracker.repository.HabitEntryRepository;
//import com.daniel.habit_tracker.repository.HabitRepository;
//import com.daniel.habit_tracker.service.HabitEntryService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
//@Testcontainers
//@Transactional
//class HabitEntryControllerIntegrationTest {
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
//    @Autowired
//    private HabitEntryRepository habitEntryRepository;
//
//    private Long habitId;
//
//    @BeforeEach
//    void setUp() {
//        Habit habit = new Habit();
//        habit.setName("Read");
//        habit.setDescription("Read 10 pages every day");
//        habit.setFrequency(Frequency.DAILY);
//        Habit savedHabit = habitRepository.save(habit);
//        habitId = savedHabit.getId();
//    }
//
//    @Test
//    void createHabitEntry_dateProvided_returnsNewEntry() throws Exception {
//        String requestBody = """
//            {
//              "date": "2026-08-25"
//            }
//            """;
//
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.date").value("2026-08-25"));
//    }
//
//    @Test
//    void createHabitEntry_noDateProvided_returnsNewEntry() throws Exception {
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
//    }
//
//    @Test
//    void createHabitEntry_duplicateDate_returnsError() throws Exception {
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.message").value("Habit already marked as done for " + LocalDate.now()));
//    }
//
//    @Test
//    void getHabitEntries_existingHabitEntries_returnsListOfHabitEntries() throws Exception {
//        String requestBody = """
//                {
//                "date" : "2026-08-25"
//                }
//                """;
//
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.date").value("2026-08-25"));
//        mockMvc.perform(post("/api/habits/" + habitId + "/entries")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
//        mockMvc.perform(get("/api/habits/" + habitId + "/entries"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].date").value(LocalDate.now().toString()))
//                .andExpect(jsonPath("$[1].date").value("2026-08-25"));
//    }
//
//}