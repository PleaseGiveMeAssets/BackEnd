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
    private Integer openPrice;
    private Integer closedPrice;
    private Integer highPrice;
    private Integer lowPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
