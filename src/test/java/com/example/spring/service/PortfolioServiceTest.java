package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.PortfolioDTO;
import com.example.spring.util.OrderTypeStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class PortfolioServiceTest {
    @Autowired
    private PortfolioService portfolioService;

    @Test
    @DisplayName("포트폴리오 종목 요약 조회 : 종목이 없으면 성공")
    public void successNotExistOrderSummary() {
        assertThrows(IllegalArgumentException.class, () -> {
            portfolioService.getOrderSummary("testUser1", 3167L);
        });
    }

    @Test
    @Transactional
    @DisplayName("포트폴리오 생성 : 종목이 없으면 성공")
    public void makePortfolio(){
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPrice(1000L);
        portfolioDTO.setQuantity(100L);
        portfolioDTO.setMemo("test");
        portfolioDTO.setOrderedAt(Timestamp.valueOf(LocalDateTime.now()));
        portfolioDTO.setOrderType(OrderTypeStatus.BUY);
        int status = portfolioService.createOrder("testUser1", 3167L, portfolioDTO);
        assertEquals(1, status);
    }

    @Test
    @Transactional
    @DisplayName("포트폴리오 종목 요약 조회 : 종목이 없으면 성공")
    public void updateOrder(){
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setPrice(1000L);
        portfolioDTO.setQuantity(100L);
        portfolioDTO.setMemo("tes");
        portfolioDTO.setOrderedAt(Timestamp.valueOf(LocalDateTime.now()));
        portfolioDTO.setOrderType(OrderTypeStatus.BUY);
        int status = portfolioService.updateOrder(7L, portfolioDTO);
        assertEquals(1, status);
    }
}
