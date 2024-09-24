package com.example.spring.dto;

import com.example.spring.vo.RecommendStockVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private int stockId;
    private String standardCode;
    private int subCategoryId;
    private List<RecommendStockVO> recommendStockVOList;
    private String stockName;
    private String shortCode;
    private char stockExchangeMarket;
    private String marketCapitalization;
    private char stockTradeStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
