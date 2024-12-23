package com.example.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME_SECONDS = TimeUnit.DAYS.toSeconds(15);

    public void saveValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public void storeRefreshToken(String userId, String refreshToken) {
        String key = "user:" + userId + ":refresh_token";
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String userId) {
        String key = "user:" + userId + ":refresh_token";
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String userId) {
        String key = "user:" + userId + ":refresh_token";
        redisTemplate.delete(key);
    }
}