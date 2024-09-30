package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHistory {
    private Long portfolioHistoryId;
    private Long portfolioId;
    private Double dailyProfit;
    private Double dailyProfitRate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
