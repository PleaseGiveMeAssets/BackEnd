package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioHistoryDTO {
    private String userId;
    private List<DailyStockProfitDTO> profitDTOList;
//    private int portfolioId;
//    private double dailyProfit;
//    private double dailyProfitRate;
//    private Timestamp createdAt;
}
