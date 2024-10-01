package com.example.spring.service;

import com.example.spring.dto.SavedNewsDTO;

import java.util.List;

public interface SavedNewsService {
    List<SavedNewsDTO> getSavedNews(String userId);
    void saveNews(SavedNewsDTO savedNewsDto);
    void deleteNews(int savedNewsId);
}
