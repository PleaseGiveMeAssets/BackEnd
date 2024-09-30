package com.example.spring.dto;

import com.example.spring.vo.StockVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private int stockId;
    private String shortCode;
    private String stockName;

    public static StockDTO of(StockVO stockVO) {
        return new StockDTO(
                stockVO.getStockId(),
                stockVO.getShortCode(),
                stockVO.getStockName()
        );
    }
}
