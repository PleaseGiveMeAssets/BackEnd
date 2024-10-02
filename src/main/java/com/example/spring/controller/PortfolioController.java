package com.example.spring.controller;

import com.example.spring.dto.PortfolioDTO;
import com.example.spring.dto.OrderSummaryDTO;
import com.example.spring.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1")
public class PortfolioController {

    public PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio/{stockId}")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        OrderSummaryDTO orderSummary = portfolioService.getOrderSummary(userDetails.getUsername(), stockId);
        return ResponseEntity.ok(orderSummary);
    }

    @PostMapping("/portfolio/{stockId}/order")
    public ResponseEntity createOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId, @RequestBody PortfolioDTO portfolioDTO) {
        portfolioService.createOrder(userDetails.getUsername(), stockId, portfolioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/portfolio/order/{orderId}")
    public ResponseEntity updateOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long orderId, @RequestBody PortfolioDTO portfolioDTO) {
        portfolioService.updateOrder(orderId, portfolioDTO);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}