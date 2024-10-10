package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserStockPortfolio {
    private int stockId;
    private String stockName;
    private String shortCode;
    private int quantity;
    private double averagePurchasePrice;
    private double totalInvestedAmount;
    private double closedPrice;
    private double totalProfitLossAmount;
    private double profitLossPercentage;
}
