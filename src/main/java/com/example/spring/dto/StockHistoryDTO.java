package com.example.spring.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class StockHistoryDTO {
    private String stockHistoryId;
    private Integer openPrice;
    private Integer closedPrice;
    private Integer highPrice;
    private Integer lowPrice;
}
