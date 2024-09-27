package com.example.spring.service;


import com.example.spring.dto.PortfolioPurchaseDTO;
import com.example.spring.vo.PortfolioVO;

public interface PortfolioPurchaseService {
    void saveStock(String userId, PortfolioPurchaseDTO portfolioPurchaseDTO);
}
