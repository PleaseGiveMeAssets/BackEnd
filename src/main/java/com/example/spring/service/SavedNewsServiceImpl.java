package com.example.spring.service;

import com.example.spring.domain.News;
import com.example.spring.domain.SavedNews;
import com.example.spring.mapper.SavedNewsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SavedNewsServiceImpl implements SavedNewsService {

    @Autowired
    private final SavedNewsMapper savedNewsMapper;

    // 유저 ID를 사용하여 저장된 뉴스를 불러옴
    @Override
    public List<News> getSavedNews(String userId) {
        log.info("유저 ID {}의 저장된 뉴스를 불러옵니다.", userId);
        return savedNewsMapper.findAllSavedNewsByUserId(userId);
    }
    // 저장된 뉴스를 DB에 저장
    @Override
    public void saveNews(Long newsId, String userId) {
        log.info("뉴스 ID {}를 유저 ID {}로 저장합니다.", newsId, userId);
        savedNewsMapper.save(new SavedNews(null, newsId, userId, null, null));
    }
    // 저장된 뉴스 삭제
    @Override
    public void deleteNews(String userId, Long newsId) {
        log.info("유저 ID {}의 저장된 뉴스 ID {}를 삭제합니다.", userId, newsId);
        savedNewsMapper.deleteByUserIdAndSavedNewsId(userId, newsId);
        log.info("유저 ID {}의 뉴스 ID {}를 성공적으로 삭제했습니다.", userId, newsId);
    }

}
