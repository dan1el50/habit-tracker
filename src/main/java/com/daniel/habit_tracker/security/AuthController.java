package com.daniel.habit_tracker.security;

import com.daniel.habit_tracker.dto.AuthResponse;
import com.daniel.habit_tracker.dto.LoginRequest;
import com.daniel.habit_tracker.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping ("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
