package com.example.spring.service;

import com.example.spring.dto.ForChartDTO;

import java.util.List;
import java.util.Map;

import com.example.spring.dto.PortfolioDTO;
import com.example.spring.dto.OrderSummaryDTO;

public interface PortfolioService {
    Map<String, List<ForChartDTO>> getOrderList(String userId);
    public OrderSummaryDTO getOrderSummary(String userId, Long stockId);
    public int updateOrder(Long orderId, PortfolioDTO portfolioDTO);
    public int createOrder(String userId, Long stockId, PortfolioDTO portfolioDTO);
}
