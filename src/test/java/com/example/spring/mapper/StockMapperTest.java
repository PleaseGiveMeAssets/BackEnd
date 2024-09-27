package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.vo.StockVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
        List<StockVO> stockVOList = stockMapper.selectListRecommendStockByUserId(userId, date);
        assertTrue(stockVOList.size() > 0);
    }

    @Test
    @Transactional
    public void insertStock(){
        StockVO stockVO = new StockVO(
                1L,                                // stockId
                "12345",                            // standardCode
                10L,
                null,// subCategoryId
                "Test Stock",                       // stockName
                "TST",                              // shortCode
                'N',                                // stockExchangeMarket ('N' for NASDAQ)
                "1000000000",                       // marketCapitalization
                '1',                                // stockTradeStatus ('A' for Active)
                null,
                null
        );
        stockMapper.insert(stockVO);
    }

    @Test
    public void selectListPortfolioByUserId() {
        String userId = "testUser1";
        List<StockVO> stockVOList = stockMapper.selectListPortfolioByUserId(userId);
        assertTrue(stockVOList.size() > 0);
    }
}
