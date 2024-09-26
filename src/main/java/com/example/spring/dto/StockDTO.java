package com.example.spring.dto;

import com.example.spring.vo.StockVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private int stockId;
    private String standardCode;
    private String stockName;
    private int mainCategory;
    private int subCategory;
    private String shortCode;
    private char stockExchangeMarket;
    private String marketCapitalization;
    private char stockTradeStatus;
    private LocalDateTime updatedAt;

    public static StockDTO of(StockVO stockVO) {
        return new StockDTO(
                stockVO.getStockId(),
                stockVO.getStandardCode(),
                stockVO.getStockName(),
                stockVO.getMainCategory(),
                stockVO.getSubCategory(),
                stockVO.getShortCode(),
                stockVO.getStockExchangeMarket(),
                stockVO.getMarketCapitalization(),
                stockVO.getStockTradeStatus(),
                stockVO.getUpdatedAt()
        );
    }
}
