package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendStockVO {
    private int recommendStockId;
    private int stockId;
    private String userId;
    private String Content;
    private int price;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
