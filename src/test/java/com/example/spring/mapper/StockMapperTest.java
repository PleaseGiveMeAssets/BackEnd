package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.domain.Stock;
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
public class StockMapperTest {
    @Autowired
    private StockMapper stockMapper;

    @Test
    public void selectListRecommendStockByUserId() {
        String userId = "testUser1";
        String date = "2024-09-26";
        List<Stock> stockList = stockMapper.selectListRecommendStockByUserId(userId, date);
        assertTrue(stockList.size() > 0);
    }

    @Test
    public void selectListPortfolioByUserId() {
        String userId = "testUser1";
        List<Stock> stockList = stockMapper.selectListPortfolioByUserId(userId);
        assertTrue(stockList.size() > 0);
    }
}
