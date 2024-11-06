package com.example.spring.controller;

import com.example.spring.domain.News;
import com.example.spring.service.SavedNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/saved-news")
@RequiredArgsConstructor
public class SavedNewsController {

    private final SavedNewsService savedNewsService;

    // 유저의 저장된 뉴스 목록을 불러옴
    @GetMapping
    public ResponseEntity<List<News>> getSavedNews(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("유저 ID {}의 저장된 뉴스 목록을 조회합니다.", userDetails.getUsername());
        return ResponseEntity.ok(savedNewsService.getSavedNews(userDetails.getUsername()));
    }
    // 뉴스를 저장
    @PostMapping("/{newsId}")
    public ResponseEntity<Void> saveNews(@PathVariable Long newsId, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("뉴스 ID {}를 유저 ID {}로 저장합니다.", newsId, userDetails.getUsername());
        savedNewsService.saveNews(newsId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
    // 저장된 뉴스를 삭제
    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteNews(@AuthenticationPrincipal UserDetails userDetails,@PathVariable Long newsId) {
        log.info("유저 ID {}의 뉴스 ID {}를 삭제합니다.", userDetails.getUsername(), newsId);
        savedNewsService.deleteNews(userDetails.getUsername(), newsId);
        return ResponseEntity.ok().build();
    }
}
