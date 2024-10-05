package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Long stockId;
    private String shortCode;
    private Long subCategoryId;
    private String stockName;
    private String standardCode;
    private Character stockExchangeMarket;
    private Long marketCapitalization;
    private BigDecimal eps;
    private BigDecimal per;
    private BigDecimal bps;
    private BigDecimal pbr;
    private Integer openPrice;
    private Integer closedPrice;
    private Integer highPrice;
    private Integer lowPrice;
    private Character stockTradeStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<Portfolio> portfolioList;
    private List<StockHistory> stockHistoryList;
    private List<RecommendStock> recommendStockList;
}
