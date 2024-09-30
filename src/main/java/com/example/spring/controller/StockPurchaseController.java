package com.example.spring.controller;

import com.example.spring.dto.StockPurchaseDTO;
import com.example.spring.service.StockPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class StockPurchaseController {
    private final StockPurchaseService stockPurchaseService;

    /**
     * 사용자의 주식 정보 추가하기(매수)
     * @param userDetails
     * @param stockPurchaseDTO
     * @return
     */
    @PostMapping("/stock/search/purchase")
    public ResponseEntity<?> purchaseDTO(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody StockPurchaseDTO stockPurchaseDTO) {
        stockPurchaseService.saveStock(userDetails.getUsername(), stockPurchaseDTO);
        log.info("사용자의 매수 정보 저장 - usernName: {}", userDetails.getUsername());
        return ResponseEntity.accepted().build();
    }


}
