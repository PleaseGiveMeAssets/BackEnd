package com.example.spring.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class StockHistoryDTO {
    private String stockHistoryId;
    private Long price;
}
