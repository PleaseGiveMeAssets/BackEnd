package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {
    private int stockId;
    private String standardCode;
    private String stockName;
    private int mainCategory;
    private int subCategory;
    private String shortCode;
    private char stockExchangeMarket;
    private String marketCapitalization;
    private char stockTradeStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
