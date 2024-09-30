package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {
    private int stockId;
    private String shortCode;
    private int subCategoryId;
    private String stockName;
    private String standardCode;
    private char stockExchangeMarket;
    private String marketCapitalization;
    private int openPrice;
    private int closedPrice;
    private int highPrice;
    private int lowPrice;
    private char stockTradeStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
