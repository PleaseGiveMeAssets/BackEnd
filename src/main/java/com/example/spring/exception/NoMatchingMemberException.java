package com.example.spring.exception;

public class NoMatchingMemberException extends RuntimeException {
    public NoMatchingMemberException(String message) {
        super(message);
    }
}