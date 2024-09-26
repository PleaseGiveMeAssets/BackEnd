package com.example.spring.service;


import com.example.spring.dto.MyStockDTO;
import com.example.spring.dto.StockDTO;

import java.util.List;

public interface SearchService {
    List<StockDTO> getStocksList(String stockName);
    List<MyStockDTO> getMyAllStocksList(String userId);
    List<MyStockDTO> getMyStocksList(String userId, String stockName);
}
