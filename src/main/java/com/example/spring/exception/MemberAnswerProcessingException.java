package com.example.spring.exception;

public class MemberAnswerProcessingException extends RuntimeException {
    public MemberAnswerProcessingException(String message) {
        super(message);
    }

}
