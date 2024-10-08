package com.example.spring.exception;

public class TotalScoreCalculationException extends RuntimeException {
    public TotalScoreCalculationException(String message, Exception e) {
        super(message);
    }
}
