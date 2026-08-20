package com.daniel.habit_tracker.exceptions;

public class HabitAlreadyCompletedException extends RuntimeException {
    public HabitAlreadyCompletedException(String message) {
        super(message);
    }
}
