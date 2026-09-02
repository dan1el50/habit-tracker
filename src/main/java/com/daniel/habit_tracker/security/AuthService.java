package com.daniel.habit_tracker.security;

import com.daniel.habit_tracker.dto.AuthResponse;
import com.daniel.habit_tracker.dto.LoginRequest;
import com.daniel.habit_tracker.dto.RegisterRequest;
import com.daniel.habit_tracker.entity.User;
import com.daniel.habit_tracker.exceptions.EmailTakenException;
import com.daniel.habit_tracker.exceptions.HabitNotFoundException;
import com.daniel.habit_tracker.exceptions.UsernameTakenException;
import com.daniel.habit_tracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request){
        String username = request.getUsername();
        String email = request.getEmail();

        if (userRepository.existsByUsername(username)){
            throw new UsernameTakenException("Username already taken.");
        }

        if (userRepository.existsByEmail(email)){
            throw new EmailTakenException("Email already taken.");
        }

        String password = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        String token = jwtService.generateToken(username);

        return new AuthResponse(token, username, user.getName(), email);
    }

    public AuthResponse login(LoginRequest request){
        String usernameOrEmail = request.getUsernameOrEmail();
        String password = request.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new HabitNotFoundException("User not found: " + usernameOrEmail));

        String token = jwtService.generateToken(user.getUsername()); // always the real username
        return new AuthResponse(token, user.getUsername(), user.getName(), user.getEmail());
    }
}
