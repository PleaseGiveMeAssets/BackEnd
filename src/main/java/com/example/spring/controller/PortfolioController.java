package com.example.spring.controller;

import com.example.spring.dto.ForChartDTO;
import com.example.spring.dto.OrderDTO;
import com.example.spring.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    /**
     * 유저 Order 내역 조회
     * UserId으로 User가 가진 총 자산 현황을 조회.
     */

    @GetMapping("/portfolio")
    public ResponseEntity<List<ForChartDTO>> getPortfolio(@AuthenticationPrincipal UserDetails userDetails) {
        List<ForChartDTO> forChartDTOList = portfolioService.getOrderList(userDetails.getUsername());
        return ResponseEntity.ok(forChartDTOList);
    }
}
