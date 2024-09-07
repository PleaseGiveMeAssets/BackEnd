package com.example.spring.mapper;

import com.example.spring.vo.ExampleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExampleMapper {
    List<ExampleVO> findAll();
}
