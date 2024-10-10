package com.example.spring.controller;

import com.example.spring.dto.DailyStockDTO;
import com.example.spring.service.DailyRecommendService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/dailyrecommend")
@Slf4j
public class DailyRecommendController {
    private final DailyRecommendService dailyRecommendService;

    @Autowired
    public DailyRecommendController(DailyRecommendService dailyRecommendService) {
        this.dailyRecommendService = dailyRecommendService;
    }

    /**
     * 일일추천종목 조회
     *
     * @param userDetails
     * @param date
     * @return
     */
    @GetMapping({"", "{date}"})
    public ResponseEntity<List<DailyStockDTO>> getDailyRecommendStockInfo(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "date", required = false) String date) {
        if (log.isInfoEnabled()) {
            log.info("getDailyRecommendStockInfo userDetails : {}, date : {}", userDetails, date);
        }
        return ResponseEntity.ok(dailyRecommendService.getDailyRecommendStockInfo(userDetails.getUsername(), date));
    }
}
