package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockVO {
    private int stockId;
    private String standardCode;
    private int subCategoryId;
    private String stockName;
    private String shortCode;
    private char stockExchangeMarket;
    private String marketCapitalization;
    private char stockTradeStatus;
    private Timestamp createAt;
    private Timestamp updateAt;

}
