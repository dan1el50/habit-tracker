package com.daniel.habit_tracker.exceptions;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException(String message){super(message);}
}
