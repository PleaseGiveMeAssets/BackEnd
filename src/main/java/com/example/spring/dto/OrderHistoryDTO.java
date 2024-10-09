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
    private Long totalPrice;
    private Long totalQuantity;
    private List<OrderDTO> orders;
}
