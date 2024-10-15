package com.example.spring.service;

import com.example.spring.domain.TotalInvestedSumStockPortfolio;
import com.example.spring.dto.StockPortfolioDTO;
import com.example.spring.dto.TotalStockInfoDTO;

import java.util.List;

public interface PortfolioHistoryService {
    List<TotalStockInfoDTO> getStockPortfolioInfo(String userId);
    List<TotalInvestedSumStockPortfolio> getUserStockPortfolioTotalInvestedAmountByDate(String userId);
    List<StockPortfolioDTO> getStockPortfolioInfoByDate(String userId);
}
