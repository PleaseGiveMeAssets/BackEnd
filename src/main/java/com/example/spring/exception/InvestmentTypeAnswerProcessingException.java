package com.example.spring.exception;

public class InvestmentTypeAnswerProcessingException extends RuntimeException {
    public InvestmentTypeAnswerProcessingException(String message) {
        super(message);
    }

    public InvestmentTypeAnswerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
