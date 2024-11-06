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
public class OrderDTO {
    private Long orderId;
    private Long price;
    private Long quantity;
    private String memo;
    private Character orderType;
    private Timestamp orderedAt;
    private Timestamp createdAt;
}
