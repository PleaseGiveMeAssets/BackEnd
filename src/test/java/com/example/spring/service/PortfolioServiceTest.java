package com.example.spring.service;

import com.example.spring.config.AppConfig;
import com.example.spring.dto.OrderDTO;
import com.example.spring.dto.OrderHistoryDTO;
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
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPrice(1000L);
        orderDTO.setQuantity(100L);
        orderDTO.setMemo("test");
        orderDTO.setOrderedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderDTO.setOrderType('B');
        int status = portfolioService.createOrder("testUser1", 3167L, orderDTO);
        assertEquals(0, status);
    }

    @Test
    @Transactional
    @DisplayName("포트폴리오 종목 요약 조회 : 종목이 없으면 성공")
    public void updateOrder(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setPrice(1000L);
        orderDTO.setQuantity(100L);
        orderDTO.setMemo("tes");
        orderDTO.setOrderedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderDTO.setOrderType('B');
        int status = portfolioService.updateOrder(7L, orderDTO);
        assertEquals(0, status);
    }

    @Test
    @Transactional
    @DisplayName("포트폴리오 종목 조회")
    public void selectOrders(){
        OrderHistoryDTO orderHistory = portfolioService.getOrders("testUser1", 27777L);
        
    }
}
