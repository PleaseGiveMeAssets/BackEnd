package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTotalStockPortfolioPowerDTO {
    private double totalInvestedAmount;
    private double totalProfitLossAmount;
    private double totalProfitLossPercentage;
}
