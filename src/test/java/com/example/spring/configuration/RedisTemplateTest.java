package com.example.spring.configuration;

import com.example.spring.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testRedisConnection() {
        redisTemplate.opsForValue().set("testKey", "testValue");

        String value = (String) redisTemplate.opsForValue().get("testKey");
        assertEquals("testValue", value);

        redisTemplate.delete("testKey");

        String deletedValue = (String) redisTemplate.opsForValue().get("testKey");
        assertNull(deletedValue);
    }
}