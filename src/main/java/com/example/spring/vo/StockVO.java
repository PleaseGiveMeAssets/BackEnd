package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {
    private Long stockId;
    private String standardCode;
    private Long subCategoryId;
    private String shortCode;
    private List<RecommendStockVO> recommendStockVOList;
    private String stockName;
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
