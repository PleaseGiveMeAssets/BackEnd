package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.DailyStockDTO;
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
public class DailyRecommendServiceTest {
    @Autowired
    private DailyRecommendService dailyRecommendService;

    @Test
    public void getDailyRecommendStockInfo() {
        String userId = "testUser1";
        String date = "2024-09-26";
        List<DailyStockDTO> dailyStockDTOList = dailyRecommendService.getDailyRecommendStockInfo(userId, date);
        assertTrue(dailyStockDTOList.size() > 0);
    }
}
