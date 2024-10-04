package com.example.spring.service;

import com.example.spring.dto.StockPortfolioDTO;

import java.util.List;

public interface StockPortfolioService {
    public List<StockPortfolioDTO> getStockPortfolioInfo(String userId);
}
