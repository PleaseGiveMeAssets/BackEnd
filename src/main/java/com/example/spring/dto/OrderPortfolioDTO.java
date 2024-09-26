package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPortfolioDTO {
    private long portfolioId;
    private String userId;
    private int price;
    private int quantity;
    private char orderType;
}
