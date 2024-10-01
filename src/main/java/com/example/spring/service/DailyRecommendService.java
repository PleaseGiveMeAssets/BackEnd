package com.example.spring.service;

import com.example.spring.dto.DailyStockDTO;

import java.util.List;

public interface DailyRecommendService {
    public List<DailyStockDTO> getDailyRecommendStockInfo(String userId, String date);
}
