package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.ExampleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class ExampleServiceTest {
    @Autowired
    private ExampleService exampleService;

    @Test
    public void getListExample() {
        List<ExampleDTO> exampleDTOList = exampleService.getListExample();
        assertTrue(exampleDTOList.size() > 0);
    }
}
