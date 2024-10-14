package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryDTO {
    private String name;
    private String shortCode;
    private Long avgPrice;
    private Long totalQuantity;
    private Long recentPrice;
}
