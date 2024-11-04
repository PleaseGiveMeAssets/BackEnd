package com.example.spring.mapper;

import com.example.spring.domain.News;
import com.example.spring.domain.SavedNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavedNewsMapper {
    List<News> findAllSavedNewsByMemberId(String memberId);

    void save(SavedNews savedNews);

    void deleteByMemberIdAndSavedNewsId(@Param("memberId") String memberId, @Param("savedNewsId") Long savedNewsId);
}
