package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockPortfolioDTO {
    private long stockId;
    private String shortCode;
    private List<OrderPortfolioDTO> orderPortfolioDTOList;
    private String stockName;
    private char stockTradeStatus;
}
