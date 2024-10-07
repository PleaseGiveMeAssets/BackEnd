package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockHistory {
    private String stockHistoryId;
    private Long stockId;
    private String shortCode;
    private Long openPrice;
    private Long closedPrice;
    private Long highPrice;
    private Long lowPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
