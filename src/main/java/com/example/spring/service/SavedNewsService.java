package com.example.spring.service;

import com.example.spring.domain.News;
import com.example.spring.dto.SavedNewsDTO;

import java.util.List;

public interface SavedNewsService {
    List<News> getSavedNews(String userId);
    void saveNews(Long newsId, String userId);
    void deleteNews(String userId, Long savedNewsId);
}
