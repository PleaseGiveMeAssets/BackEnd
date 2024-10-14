package com.example.spring.service;

import com.example.spring.domain.News;
import com.example.spring.dto.NewsDTO;
import com.example.spring.mapper.NewsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public List<NewsDTO> findNewsByStockId(Long stockId) {
        List<News> newsList = newsMapper.selectNewsByStockId(stockId);
        List<NewsDTO> newsDTOList = new ArrayList<>();

        log.info("hi : {}", newsList.get(0).getImage());

        for (News news : newsList) {
            NewsDTO newsDTO = new NewsDTO();
            newsDTO.setNewsId(news.getNewsId());
            newsDTO.setTitle(news.getTitle());
            newsDTO.setLink(news.getLink());
            newsDTO.setImage(news.getImage());
            newsDTOList.add(newsDTO);
        }

        return newsDTOList;
    }
}
