package com.example.spring.service;

import com.example.spring.dto.*;

import java.util.List;

public interface PortfolioService {
    public List<ForChartDTO> getOrderList(String userId);

    public OrderHistoryDTO getOrders(String username, Long stockId);

    public OrderSummaryDTO getOrderSummary(String userId, Long stockId);

    public int updateOrder(Long orderId, OrderDTO orderDTO);

    public int createOrder(String userId, Long stockId, OrderDTO orderDTO);

    public int deleteOrders(OrderDeleteDTO orderDeleteDTO);

    public int deleteALlOrder(String userId, Long stockId);
}
