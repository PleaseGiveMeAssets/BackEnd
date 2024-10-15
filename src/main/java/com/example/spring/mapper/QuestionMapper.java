package com.example.spring.mapper;

import com.example.spring.domain.Question;
import com.example.spring.domain.QuestionOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    //특정 질문 조회
    Question selectSurveyQuestion(@Param("questionId") long questionId);

    // 특정 질문에 대한 옵션들 조회
    List<QuestionOption> selectOptionsByQuestionId(@Param("questionId") long questionId);

    int getTotalQuestionsCount();
}
