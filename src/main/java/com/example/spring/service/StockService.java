package com.example.spring.service;

import com.example.spring.domain.EditStockPortfolio;
import com.example.spring.domain.Stock;
import com.example.spring.domain.UserStockPortfolio;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.dto.UserTotalStockPortfolioPowerDTO;

import java.sql.Timestamp;
import java.util.List;

public interface StockService {
    List<StockHistoryDTO> findByStockId(Long stockId);
    StockIndexDTO findIndexByStockId(Long stockId);
    List<UserStockPortfolio> getUserStockPortfolio(String userId);
    EditStockPortfolio getUserStockPortfolioByDate(String userId, Timestamp timestamp, Long stockId);
    UserTotalStockPortfolioPowerDTO getUserTotalStockPortfolio(String userId);

}
