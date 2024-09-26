package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyStockDTO {
    private long stockId;
    private String shortCode;
    private String stockName;
    private List<DailyRecommendStockDTO> recommendStockVOList;
}
