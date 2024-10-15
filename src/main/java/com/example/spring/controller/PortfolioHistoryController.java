package com.example.spring.controller;

import com.example.spring.dto.TotalStockInfoDTO;
import com.example.spring.service.PortfolioHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/weekly-graph")
@Slf4j
@RequiredArgsConstructor
public class PortfolioHistoryController {
    private final PortfolioHistoryService portfolioHistoryService;

    @GetMapping
    public ResponseEntity<List<TotalStockInfoDTO>> getStockPortfolioInfo(@AuthenticationPrincipal UserDetails userDetails) {
        List<TotalStockInfoDTO> totalStockInfoDTOS = portfolioHistoryService.getStockPortfolioInfo(userDetails.getUsername());
        totalStockInfoDTOS.forEach(totalStockInfoDTO -> {
            log.info("********(*(*(*(*(*(*(*(* totalStockInfoDTO : {} ", totalStockInfoDTO.toString());
        });
        return ResponseEntity.ok(portfolioHistoryService.getStockPortfolioInfo(userDetails.getUsername()));
    }
}
