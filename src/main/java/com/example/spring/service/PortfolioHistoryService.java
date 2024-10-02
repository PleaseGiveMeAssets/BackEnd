package com.example.spring.service;

import com.example.spring.dto.TotalStockInfoDTO;

import java.util.List;

public interface PortfolioHistoryService {
    public List<TotalStockInfoDTO> saveStockPortfolioInfo();
}
