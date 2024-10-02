package com.example.spring.exception;

public class UserAnswerProcessingException extends RuntimeException {
    public UserAnswerProcessingException(String message) {
        super(message);
    }

    public UserAnswerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
