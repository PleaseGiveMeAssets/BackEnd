package com.example.spring.dto;

import com.example.spring.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForChartDTO {
    private String stockName;
    private int totalQuantity;
    private double totalPrice;
}
