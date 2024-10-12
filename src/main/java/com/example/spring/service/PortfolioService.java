package com.example.spring.service;

import com.example.spring.dto.*;

import java.util.List;

public interface PortfolioService {
    List<ForChartDTO> getOrderList(String userId);

    OrderHistoryDTO getOrders(String userId, Long stockId);

    OrderSummaryDTO getOrderSummary(String userId, Long stockId);

    int updateOrder(Long orderId, OrderDTO orderDTO);

    int createOrder(String userId, Long stockId, OrderDTO orderDTO);

    int deleteOrders(OrderDeleteDTO orderDeleteDTO);

    int deleteALlOrder(String userId, Long stockId);
}
