package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockIndexDTO {
    private Long marketCapitalization;
    private BigDecimal eps;
    private BigDecimal per;
    private BigDecimal bps;
    private BigDecimal pbr;
}
