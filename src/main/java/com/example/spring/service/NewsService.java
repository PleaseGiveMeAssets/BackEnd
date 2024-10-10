package com.example.spring.service;

import com.example.spring.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    List<NewsDTO> findNewsByStockId(Long stockId);
}
