package com.example.spring.controller;

import com.example.spring.dto.StockHistoryDTO;
import com.example.spring.dto.StockIndexDTO;
import com.example.spring.service.StockHistoryService;
import com.example.spring.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    @Autowired
    public StockController(StockHistoryService stockHistoryService, StockService stockService) {
        this.stockService = stockService;
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<List<StockHistoryDTO>> getStockHistories(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        return ResponseEntity.ok(stockHistoryService.findByStockId(stockId));
    }

    @GetMapping("/{stockId}/index")
    public ResponseEntity<StockIndexDTO> getStockIndex(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long stockId) {
        return ResponseEntity.ok(stockService.findIndexByStockId(stockId));
    }
}
