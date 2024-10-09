package com.example.spring.controller;

import com.example.spring.domain.News;
import com.example.spring.service.SavedNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/saved-news")
@RequiredArgsConstructor
public class SavedNewsController {

    private final SavedNewsService savedNewsService;

    // 유저의 저장된 뉴스 목록을 불러옴
    @GetMapping("/{userId}")
    public ResponseEntity<List<News>> getSavedNews(@PathVariable("userId") String userId) {
        log.info("유저 ID {}의 저장된 뉴스 목록을 조회합니다.", userId);
        return ResponseEntity.ok(savedNewsService.getSavedNews(userId));
    }
    // 뉴스를 저장
    @PostMapping("/{newsId}/{userId}")
    public ResponseEntity<Void> saveNews(@PathVariable Long newsId, @PathVariable String userId) {
        log.info("뉴스 ID {}를 유저 ID {}로 저장합니다.", newsId, userId);
        savedNewsService.saveNews(newsId, userId);
        return ResponseEntity.ok().build();
    }
    // 저장된 뉴스를 삭제
    @DeleteMapping("/{userId}/{newsId}")
    public ResponseEntity<Void> deleteNews(@PathVariable String userId, @PathVariable Long newsId) {
        log.info("유저 ID {}의 뉴스 ID {}를 삭제합니다.", userId, newsId);
        savedNewsService.deleteNews(userId, newsId);
        return ResponseEntity.ok().build();
    }
}
