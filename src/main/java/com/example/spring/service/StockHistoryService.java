package com.example.spring.service;

import com.example.spring.dto.StockHistoryDTO;

import java.util.List;

public interface StockHistoryService {
    List<StockHistoryDTO> findByStockId(Long stockId);
}
