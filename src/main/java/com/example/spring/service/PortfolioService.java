package com.example.spring.service;

import com.example.spring.dto.*;

import java.util.List;

public interface PortfolioService {
    List<ForChartDTO> getOrderList(String memberId);

    OrderHistoryDTO getOrders(String memberId, Long stockId);

    OrderSummaryDTO getOrderSummary(String memberId, Long stockId);

    int updateOrder(Long orderId, OrderDTO orderDTO);

    int createOrder(String memberId, Long stockId, OrderDTO orderDTO);

    int deleteOrders(OrderDeleteDTO orderDeleteDTO);

    int deleteALlOrder(String memberId, Long stockId);
}
