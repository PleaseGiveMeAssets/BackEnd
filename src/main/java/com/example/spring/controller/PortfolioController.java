package com.example.spring.controller;

import com.example.spring.dto.ForChartDTO;
import com.example.spring.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio")
@Slf4j
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;
    /**
     *
     * @param userDetails
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<ForChartDTO>> getPortfolio(@RequestParam String userDetails) {
        List<ForChartDTO> forChartDTOList = portfolioService.getOrderList(userDetails);
        return ResponseEntity.ok(forChartDTOList);
    }
}
