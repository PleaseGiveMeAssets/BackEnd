package com.example.spring.exception;

public class UserAnswerProcessingException extends RuntimeException {
    public UserAnswerProcessingException(String message) {
        super(message);
    }

}
