package com.example.spring.service;

import com.example.spring.domain.Stock;
import com.example.spring.dto.StockIndexDTO;

public interface StockService {
    StockIndexDTO findIndexByStockId(Long stockId);
}
