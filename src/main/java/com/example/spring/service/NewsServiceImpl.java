package com.example.spring.service;

import com.example.spring.domain.News;
import com.example.spring.dto.NewsDTO;
import com.example.spring.mapper.NewsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService{
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public NewsServiceImpl(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }
    @Override
    public List<NewsDTO> findNewsByStockId(Long stockId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        NewsMapper mapper = sqlSession.getMapper(NewsMapper.class);
        List<News> newsList = mapper.selectNewsByStockId(stockId);
        List<NewsDTO> newsDTOList = new ArrayList<>();

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
