package com.example.spring.controller;

import com.example.spring.dto.PortfolioPurchaseDTO;
import com.example.spring.service.PortfolioPurchaseService;
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
public class PortfolioPurchaseController {
    private final PortfolioPurchaseService portfolioPurchaseService;


    @PostMapping("/stock/search/purchase")
    public ResponseEntity<?> purchaseDTO(@RequestParam("userDetails") String userDetails,
//            @AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody PortfolioPurchaseDTO portfolioPurchaseDTO)
    {
        portfolioPurchaseService.saveStock(userDetails,portfolioPurchaseDTO);
        return ResponseEntity.accepted().build();
    }


}
