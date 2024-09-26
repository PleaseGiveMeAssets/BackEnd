package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.vo.StockVO;
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
        String userId = "testUser";
        String date = "date";
        List<StockVO> stockVOList = stockMapper.selectListRecommendStockByUserId(userId, date);
        assertTrue(stockVOList.size() > 0);
    }
}
