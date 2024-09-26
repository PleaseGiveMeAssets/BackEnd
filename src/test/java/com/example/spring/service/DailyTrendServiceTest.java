package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.DailyTrendReportDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class DailyTrendServiceTest {
    @Autowired
    private DailyTrendService dailyTrendService;

    @Test
    public void getDailyTrendInfo() {
        String userId = "testUser1";
        DailyTrendReportDTO dailyReportDTO = dailyTrendService.getDailyTrendInfo(userId);
        assertNotNull(dailyReportDTO);
    }
}
