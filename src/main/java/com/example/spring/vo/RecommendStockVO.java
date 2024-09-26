package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendStockVO {
    private long recommendStockId;
    private String userId;
    private long stockId;
    private String shortCode;
    private String Content;
    private int price;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
