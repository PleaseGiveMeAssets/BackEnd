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
public class MyStockDTO {
    private String stockName;
    private int totalQuantity;
    private int mainCategory;
    private int subCategory;
    private LocalDateTime updatedAt;

}
