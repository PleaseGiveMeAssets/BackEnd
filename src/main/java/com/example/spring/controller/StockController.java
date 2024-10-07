package com.example.spring.controller;

import com.example.spring.domain.UserStockPortfolio;
import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.dto.UserTotalStockPortfolioPowerDTO;
import com.example.spring.service.StockHistoryService;
import com.example.spring.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * 종목 조회
     * <p>
     * 일주일치의 종목 가격을 조회하는 메소드이다.
     *
     * @param stockId
     * @return List<StockHistoryDTO>
     */
    @GetMapping("/{stockId}")
    public ResponseEntity<List<StockHistoryDTO>> getStockHistories(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.findByStockId(stockId));
    }

    @GetMapping("/{stockId}/index")
    public ResponseEntity<StockIndexDTO> getStockIndex(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.findIndexByStockId(stockId));
    }
    @GetMapping()
    public ResponseEntity<List<UserStockPortfolio>> getUserStockPortfolio(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(stockService.getUserStockPortfolio(userDetails.getUsername()));
    }

    @GetMapping("/total")
    public ResponseEntity<UserTotalStockPortfolioPowerDTO> getUserTotalStockPortfolio(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(stockService.getUserTotalStockPortfolio(userDetails.getUsername()));
    }
}
