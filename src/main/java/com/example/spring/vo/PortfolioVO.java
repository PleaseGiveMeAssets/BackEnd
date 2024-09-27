package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioVO {
    private int orderId;
    private String userId;
    private int stockId;
    private String shortCode;
    private BigInteger price;
    private int quantity;
    private String memo;
    private char orderType;
    private LocalDateTime orderedAt;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

}
