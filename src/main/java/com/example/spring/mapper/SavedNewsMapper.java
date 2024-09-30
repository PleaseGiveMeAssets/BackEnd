package com.example.spring.mapper;

import com.example.spring.dto.SavedNewsDTO;
import com.example.spring.vo.SavedNewsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavedNewsMapper {
    List<SavedNewsVO> getSavedNewsByUserId(String userId);
    void insertSavedNews(SavedNewsVO savedNewsVO);
    void deleteSavedNews(int savedNewsId);
}
