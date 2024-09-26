package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioVO {
    private int portfolioId;
    private String userId;
    private int stockId;
    private String shortCode;
    private int price;
    private int quantity;
    private String memo;
    private char orderType;
    private Date orderedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
