package com.example.spring.controller;


import com.example.spring.dto.StockDTO;
import com.example.spring.service.SearchStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/stock/search")
@Slf4j
@RequiredArgsConstructor
@RestController
public class SearchStockController {
    private final SearchStockService searchStockService;

    /**
     * @param searchStock
     * @return
     */
    @GetMapping
    public ResponseEntity<List<StockDTO>> getStockList(@RequestParam(value = "searchStock", required = false) String searchStock) {
        if (searchStock == null) {
            log.info("searchStock is null");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(searchStockService.getStocksList(searchStock));
    }
}
