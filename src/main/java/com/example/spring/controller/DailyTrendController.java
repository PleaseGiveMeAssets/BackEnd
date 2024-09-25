package com.example.spring.controller;

import com.example.spring.dto.DailyReportDTO;
import com.example.spring.service.DailyTrendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dailytrend")
@Slf4j
public class DailyTrendController {
    private DailyTrendService dailyTrendService;

    @Autowired
    public DailyTrendController(DailyTrendService dailyTrendService) {
        this.dailyTrendService = dailyTrendService;
    }

    @GetMapping
    public ResponseEntity<DailyReportDTO> getDailyTrendInfo(@AuthenticationPrincipal UserDetails userDetails) {
        if (log.isInfoEnabled()) {
            log.info("getDailyTrendInfo userDetails : {}", userDetails);
        }
        return ResponseEntity.ok(dailyTrendService.getDailyTrendInfo(userDetails.getUsername()));
    }
}
