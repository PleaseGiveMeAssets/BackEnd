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
public class OrderHistoryDTO {
    private String name;
    private String shortCode;
    private Long avgPrice;
    private Long totalQuantity;
    private Long recentPrice;
    private List<OrderDTO> orders;
}
