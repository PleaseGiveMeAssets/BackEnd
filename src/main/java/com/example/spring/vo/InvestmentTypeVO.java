package com.example.spring.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InvestmentTypeVO {
    private final String InvestmentTypeName; // 예: "Low", "Medium", "High"

}
