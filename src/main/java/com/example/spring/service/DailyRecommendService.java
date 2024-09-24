package com.example.spring.service;

import com.example.spring.dto.StockDTO;

import java.util.List;

public interface DailyRecommendService {
    public List<StockDTO> getDailyRecommendStockInfo(String userId, String date);
}
