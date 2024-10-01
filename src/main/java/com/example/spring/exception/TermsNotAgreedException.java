package com.example.spring.exception;

public class TermsNotAgreedException extends RuntimeException {
    public TermsNotAgreedException(String message) {
        super(message);
    }
}