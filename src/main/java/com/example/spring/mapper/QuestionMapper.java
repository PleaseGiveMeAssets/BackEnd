package com.example.spring.mapper;

import com.example.spring.vo.QuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    QuestionVO selectSurveyQuestion(@Param("questionId") int questionId);
}
