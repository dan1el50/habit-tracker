package com.daniel.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String name;
    private String email;
}
