package com.example.noteapi.exception;

public class LoginArgumentNotValidException extends RuntimeException {
    public LoginArgumentNotValidException(String message) {
        super(message);
    }
}
