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

@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@RestController
public class SearchStockController {
    private final SearchStockService searchStockService;

    /**
     *
     * @param searchStock
     * @return
     */
    @GetMapping("/stock/search")
    public ResponseEntity<List<StockDTO>> getStockList(@RequestParam(value = "searchStock",required = false) String searchStock){
        if(searchStock == null){
            return ResponseEntity.noContent().build();
        }
        List<StockDTO> stockList = searchStockService.getStocksList(searchStock);
        return ResponseEntity.ok(stockList);
    }
}
