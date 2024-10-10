package com.example.spring.controller;

import com.example.spring.dto.*;
import com.example.spring.service.PortfolioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/portfolio")
public class PortfolioController {
    public PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{stockId}/summary")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        OrderSummaryDTO orderSummary = portfolioService.getOrderSummary(userDetails.getUsername(), stockId);
        return ResponseEntity.ok(orderSummary);
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<OrderHistoryDTO> getOrders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        return ResponseEntity.ok(portfolioService.getOrders(userDetails.getUsername(), stockId));
    }

    @PostMapping("/{stockId}")
    public ResponseEntity createOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId, @RequestBody OrderDTO orderDTO) {
        int result = portfolioService.createOrder(userDetails.getUsername(), stockId, orderDTO);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/order/{orderId}")
    public ResponseEntity updateOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        int result = portfolioService.updateOrder(orderId, orderDTO);
        if (result > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @param userDetails
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ForChartDTO>> getPortfolio(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(portfolioService.getOrderList(userDetails.getUsername()));
    }

    @DeleteMapping("/order")
    public ResponseEntity deleteOrders(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrderDeleteDTO orderDeleteDTO) {
        int result = portfolioService.deleteOrders(orderDeleteDTO);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity deleteAllOrder(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        int result = portfolioService.deleteALlOrder(userDetails.getUsername(), stockId);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}