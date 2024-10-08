package com.example.spring.controller;

import com.example.spring.dto.NewsDTO;
import com.example.spring.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@Slf4j
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<List<NewsDTO>> getNews(@PathVariable Long stockId){
        return ResponseEntity.ok(newsService.findNewsByStockId(stockId));
    }
}
