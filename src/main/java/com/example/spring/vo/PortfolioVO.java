package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioVO {
    private Long portfolioId;
    private String userId;
    private Long stockId;
    private String shortCode;
    private Long price;
    private Long quantity;
    private String memo;
    private char orderType;
    private Timestamp orderedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
