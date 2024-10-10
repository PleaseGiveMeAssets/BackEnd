package com.example.spring.mapper;

import com.example.spring.domain.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper {
    List<News> selectNewsByStockId(Long stockId);
}
