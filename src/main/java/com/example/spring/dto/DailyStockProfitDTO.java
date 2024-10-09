package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyStockProfitDTO {
        private int stockId;
        private int totalBuyQuantity;
        private int totalSellQuantity;
        private long totalBuyAmount;
        private double profit;
        private double profitRate;
        private int profitAmount;
}
