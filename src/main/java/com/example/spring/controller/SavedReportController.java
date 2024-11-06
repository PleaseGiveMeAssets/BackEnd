package com.example.spring.controller;

import com.example.spring.domain.DailyReport;
import com.example.spring.service.SavedReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/saved-reports")
@RequiredArgsConstructor
public class SavedReportController {

    private final SavedReportService savedReportService;

    // 유저의 저장된 리포트 목록을 불러옴
    @GetMapping
    public ResponseEntity<List<DailyReport>> getSavedReports(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("유저 ID {}의 저장된 리포트 목록을 조회합니다.", userDetails.getUsername());
        return ResponseEntity.ok(savedReportService.getSavedReports(userDetails.getUsername()));
    }
    // 리포트를 저장
    @PostMapping("/{dailyReportId}")
    public ResponseEntity<Void> saveReport(@PathVariable Long dailyReportId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("리포트 ID {}를 유저 ID {}로 저장합니다.", dailyReportId, userDetails.getUsername());
        savedReportService.saveReport(dailyReportId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
    // 저장된 리포트를 삭제
    @DeleteMapping("/{dailyReportId}")
    public ResponseEntity<Void> deleteReport(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long dailyReportId) {
        log.info("유저 ID {}의 리포트 ID {}를 삭제합니다.", userDetails.getUsername(), dailyReportId);
        savedReportService.deleteReport(userDetails.getUsername(), dailyReportId);
        return ResponseEntity.ok().build();
    }
}
