package com.example.spring.controller;

import com.example.spring.domain.DailyReport;
import com.example.spring.service.SavedReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/saved-reports")
@RequiredArgsConstructor
public class SavedReportController {

    private final SavedReportService savedReportService;

    // 유저의 저장된 리포트 목록을 불러옴
    @GetMapping("/{userId}")
    public ResponseEntity<List<DailyReport>> getSavedReports(@PathVariable String userId) {
        log.info("유저 ID {}의 저장된 리포트 목록을 조회합니다.", userId);
        return ResponseEntity.ok(savedReportService.getSavedReports(userId));
    }
    // 리포트를 저장
    @PostMapping("/{dailyReportId}/{userId}")
    public ResponseEntity<Void> saveReport(@PathVariable Long dailyReportId, @PathVariable String userId) {
        log.info("리포트 ID {}를 유저 ID {}로 저장합니다.", dailyReportId, userId);
        savedReportService.saveReport(dailyReportId, userId);
        return ResponseEntity.ok().build();
    }
    // 저장된 리포트를 삭제
    @DeleteMapping("/{userId}/{dailyReportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable String userId, @PathVariable Long dailyReportId) {
        log.info("유저 ID {}의 리포트 ID {}를 삭제합니다.", userId, dailyReportId);
        savedReportService.deleteReport(userId, dailyReportId);
        return ResponseEntity.ok().build();
    }
}
