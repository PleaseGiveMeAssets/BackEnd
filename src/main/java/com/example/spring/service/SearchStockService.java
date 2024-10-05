package com.example.spring.service;


import com.example.spring.dto.StockDTO;

import java.util.List;

public interface SearchStockService {
    List<StockDTO> getStocksList(String stockName);
}
