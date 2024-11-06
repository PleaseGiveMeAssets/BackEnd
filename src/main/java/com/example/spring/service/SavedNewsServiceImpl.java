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
    public List<News> getSavedNews(String memberId) {
        log.info("유저 ID {}의 저장된 뉴스를 불러옵니다.", memberId);
        return savedNewsMapper.findAllSavedNewsByMemberId(memberId);
    }
    // 저장된 뉴스를 DB에 저장
    @Override
    public void saveNews(Long newsId, String memberId) {
        log.info("뉴스 ID {}를 유저 ID {}로 저장합니다.", newsId, memberId);
        savedNewsMapper.save(new SavedNews(null, newsId, memberId, null, null));
    }
    // 저장된 뉴스 삭제
    @Override
    public void deleteNews(String memberId, Long newsId) {
        log.info("유저 ID {}의 저장된 뉴스 ID {}를 삭제합니다.", memberId, newsId);
        savedNewsMapper.deleteByMemberIdAndSavedNewsId(memberId, newsId);
        log.info("유저 ID {}의 뉴스 ID {}를 성공적으로 삭제했습니다.", memberId, newsId);
    }

}
