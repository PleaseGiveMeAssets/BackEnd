package com.example.spring.service;

import com.example.spring.dto.OrderDTO;

import java.util.List;

public interface PortfolioService {
    List<OrderDTO> getOrderList(String userId);
}
