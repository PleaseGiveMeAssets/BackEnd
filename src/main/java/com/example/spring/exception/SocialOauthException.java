package com.example.spring.exception;

public class SocialOauthException extends RuntimeException {
    public SocialOauthException(String message) {
        super(message);
    }
}