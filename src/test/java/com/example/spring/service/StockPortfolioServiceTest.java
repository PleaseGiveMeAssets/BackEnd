package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.ForChartDTO;
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
public class StockPortfolioServiceTest {
    @Autowired
    private PortfolioService stockPortfolioService;

    @Test
    public void getStockPortfolioInfo() {
        String userId = "testUser1";
        List<ForChartDTO> orderList = stockPortfolioService.getOrderList(userId);
        assertTrue(!orderList.isEmpty());
    }
}
