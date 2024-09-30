package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendStock {
    private Long recommendStockId;
    private String userId;
    private Long stockId;
    private String shortCode;
    private String content;
    private Integer price;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
