package com.example.spring.exception;

public class InvestmentTypeRetrievalException extends RuntimeException {
    public InvestmentTypeRetrievalException(String message) {
        super(message);
    }

    public InvestmentTypeRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
