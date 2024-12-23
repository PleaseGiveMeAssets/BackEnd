package com.example.spring.service;

import com.example.spring.config.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    public void saveTest(){
        String value = "Hello Redis!";
        redisService.saveValue("myKey", value);

        Assertions.assertEquals(redisService.getValue("myKey"), value);
    }
}
