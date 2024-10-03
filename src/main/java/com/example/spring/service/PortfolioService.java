package com.example.spring.service;

import com.example.spring.dto.OrderDTO;
import com.example.spring.dto.OrderDeleteDTO;
import com.example.spring.dto.OrderHistoryDTO;
import com.example.spring.dto.OrderSummaryDTO;

import java.util.List;

public interface PortfolioService {
    public OrderSummaryDTO getOrderSummary(String userId, Long stockId);
    public int updateOrder(Long orderId, OrderDTO orderDTO);
    public int createOrder(String userId, Long stockId, OrderDTO orderDTO);

    public int deleteOrders(OrderDeleteDTO orderDeleteDTO);

    public int deleteALlOrder(String userId, Long stockId);

    public OrderHistoryDTO getOrders(String username, Long stockId);
}
