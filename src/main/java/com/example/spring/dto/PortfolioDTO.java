package com.example.spring.dto;

import com.example.spring.util.OrderTypeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {
    private Long price;
    private Long quantity;
    private String memo;
    private OrderTypeStatus orderType;
    private Timestamp orderedAt;
}
