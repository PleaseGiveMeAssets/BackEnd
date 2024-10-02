package com.example.spring.mapper;

import com.example.spring.domain.News;
import com.example.spring.domain.SavedNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavedNewsMapper {
    List<News> findAllSavedNewsByUserId(String userId);

    void save(SavedNews savedNews);

    void deleteByUserIdAndSavedNewsId(@Param("userId") String userId, @Param("savedNewsId") Long savedNewsId);
}
