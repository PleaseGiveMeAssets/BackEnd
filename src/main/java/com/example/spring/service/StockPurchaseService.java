package com.example.spring.service;


import com.example.spring.dto.StockPurchaseDTO;

public interface StockPurchaseService {
    void saveStock(String userId, StockPurchaseDTO stockPurchaseDTO);
}
