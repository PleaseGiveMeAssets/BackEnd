package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.domain.DailyReport;
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
public class DailyReportMapperTest {
    @Autowired
    private DailyReportMapper dailyReportMapper;

    @Test
    public void selectDailyReportByUserId() {
        String userId = "testUser1";
        List<DailyReport> dailyReportList = dailyReportMapper.selectDailyReportByUserId(userId);
        assertTrue(dailyReportList.size() > 0);
    }
}
