package com.daniel.habit_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
