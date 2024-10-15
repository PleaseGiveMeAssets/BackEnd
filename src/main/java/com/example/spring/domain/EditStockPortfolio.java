package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditStockPortfolio {
    private Long stockId;
    private String stockName;
    private String shortCode;
    private int quantity;
    private double averagePurchasePrice;
    private double totalInvestedAmount;
}
