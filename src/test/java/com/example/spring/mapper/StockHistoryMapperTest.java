package com.example.spring.mapper;

import com.example.spring.config.AppConfig;
import com.example.spring.domain.StockHistory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Slf4j
public class StockHistoryMapperTest {
    @Autowired
    private StockHistoryMapper stockHistoryMapper;

    @Test
    public void selectStockHistoryByStockId(){
        Long stockId = 2718L;
        List<StockHistory> stockHistories = stockHistoryMapper.findByStockId(stockId);
        log.info("stockHistories = {}", stockHistories.toString());
        assertTrue(!stockHistories.isEmpty());
    }
}
