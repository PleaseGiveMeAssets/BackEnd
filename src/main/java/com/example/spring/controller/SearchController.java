package com.example.spring.controller;


import com.example.spring.dto.MyStockDTO;
import com.example.spring.dto.StockDTO;
import com.example.spring.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    /**
     * 모든 주식 검색
     * keyword를 포함하는 모든 주식명을 검색한다.
     */
    @GetMapping("/stock/search")
    public ResponseEntity<List<StockDTO>> getStockList(@RequestParam(value = "searchStock",required = false) String searchStock){
        if(searchStock == null){
            return ResponseEntity.noContent().build();
        }
        List<StockDTO> stockList = searchService.getStocksList(searchStock);
        return ResponseEntity.ok(stockList);
    }

    /**
     * 매도 할 주식 검색
     * keyword를 포함하는 User가 가진 주식명을 검색한다.
     */
//    @AuthenticationPrincipal UserDetails userDetails
    @GetMapping("/stock/search/sell")
    public ResponseEntity<List<MyStockDTO>> getMyStockList(@RequestParam String userId,
                                                         @RequestParam(value = "searchStock",required = false) String searchStock){
        if(searchStock == null){
            List<MyStockDTO> myAllStocksList = searchService.getMyAllStocksList(userId);
            log.info(myAllStocksList.toString());
            return ResponseEntity.ok(myAllStocksList);
        }

        List<MyStockDTO> myStockList = searchService.getMyStocksList(userId, searchStock);
        return ResponseEntity.ok(myStockList);
    }

}
