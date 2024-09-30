package com.example.spring.controller;

import com.example.spring.dto.StockPortfolioDTO;
import com.example.spring.service.StockPortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stockportfolio")
@Slf4j
public class StockPortfolioController {
    private final StockPortfolioService stockPortfolioService;

    @Autowired
    public StockPortfolioController(StockPortfolioService stockPortfolioService) {
        this.stockPortfolioService = stockPortfolioService;
    }

    @GetMapping
    public ResponseEntity<List<StockPortfolioDTO>> getStockPortfolioInfo() {
        return ResponseEntity.ok(stockPortfolioService.getStockPortfolioInfo("testUser1"));
    }
}
