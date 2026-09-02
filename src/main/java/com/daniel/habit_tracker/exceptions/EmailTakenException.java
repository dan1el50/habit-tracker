package com.daniel.habit_tracker.exceptions;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String message){super(message);}
}
