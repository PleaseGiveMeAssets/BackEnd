package com.example.spring.controller;

import com.example.spring.dto.OrderDTO;
import com.example.spring.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    /**
     * 유저 거래 내역 조회
     * UserId로 거래 목록을 조회한다.
     */

    @GetMapping("/portfolio/{userId}")
    public ResponseEntity<List<OrderDTO>> getPortfolio(@PathVariable String userId) {
        List<OrderDTO> orderDTOList = portfolioService.getPortfolio(userId);
        return ResponseEntity.ok(orderDTOList);
    }
}
