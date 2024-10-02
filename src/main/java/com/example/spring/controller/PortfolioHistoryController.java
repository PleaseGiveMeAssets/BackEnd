package com.example.spring.controller;

import com.example.spring.service.PortfolioHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/saveProfit")
@Slf4j
@RequiredArgsConstructor
public class PortfolioHistoryController {
    private final PortfolioHistoryService portfolioHistoryService;

    // 00시에 수익률 계산
//  @Scheduled(cron = "0 0 19 * * ?")
    @GetMapping
    public ResponseEntity<?> scheduleDailyProfitCalculation() {
        portfolioHistoryService.saveStockPortfolioInfo();
        return ResponseEntity.ok().build();
    }
}
