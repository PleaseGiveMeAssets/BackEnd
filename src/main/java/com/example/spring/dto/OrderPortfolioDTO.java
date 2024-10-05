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
    private Long portfolioId;
    private String userId;
    private Long price;
    private Long quantity;
    private Character orderType;
}
