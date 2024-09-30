package com.example.spring.mapper;

import com.example.spring.domain.SavedNews;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavedNewsMapper {
    List<SavedNews> getSavedNewsByUserId(String userId);

    void insertSavedNews(SavedNews savedNews);

    void deleteSavedNews(int savedNewsId);
}
