package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalInvestedSumStockPortfolio {
    private String stockDate;
    private Long totalInvestedSum;
    private Long calInvestedSum;
}
