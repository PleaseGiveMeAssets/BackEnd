package com.example.spring.exception;

public class NoMatchingUserException extends RuntimeException {
    public NoMatchingUserException(String message) {
        super(message);
    }
}