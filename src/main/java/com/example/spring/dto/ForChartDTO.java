package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForChartDTO {
    private Long stockId;
    private String shortCode;
    private String stockName;
    private Character stockTradeStatus;
    private Long totalQuantity;
    private Long totalPrice;
}
