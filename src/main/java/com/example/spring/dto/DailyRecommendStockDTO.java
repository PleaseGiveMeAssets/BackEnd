package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRecommendStockDTO {
    private String shortCode;
    private String stockName;
    private String content;
    private Integer price;
    private Integer changeAmount;
    private Double changeAmountRate;
}
