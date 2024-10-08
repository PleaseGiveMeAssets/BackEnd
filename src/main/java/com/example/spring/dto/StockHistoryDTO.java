package com.example.spring.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class StockHistoryDTO {
    private String stockHistoryId;
    private Long openPrice;
    private Long closedPrice;
    private Long highPrice;
    private Long lowPrice;
}
