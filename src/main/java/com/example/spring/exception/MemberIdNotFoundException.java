package com.example.spring.exception;

public class MemberIdNotFoundException extends RuntimeException {
    public MemberIdNotFoundException(String message) {
        super(message);
    }
}
