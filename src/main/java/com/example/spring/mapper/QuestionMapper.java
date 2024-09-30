package com.example.spring.mapper;

import com.example.spring.vo.QuestionOptionVO;
import com.example.spring.vo.QuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //특정 질문 조회
    QuestionVO selectSurveyQuestion(@Param("questionId") int questionId);

    // 특정 질문에 대한 옵션들 조회
    List<QuestionOptionVO> selectOptionsByQuestionId(@Param("questionId") int questionId);
}
