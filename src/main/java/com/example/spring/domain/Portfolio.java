package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    private Long portfolioId;
    private String userId;
    private Long stockId;
    private String shortCode;
    private Long price;
    private Long quantity;
    private String memo;
    private Character orderType;
    private Timestamp orderedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private PortfolioHistory portfolioHistory;
}
