package com.example.spring.mapper;

import com.example.spring.vo.UserAnswerVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserAnswerMapper {

    // 사용자 답변 입력 및 업데이트
    int insertUserAnswer(UserAnswerVO userAnswerVO);
    int updateUserAnswer(UserAnswerVO userAnswerVO);

    // 추가: 사용자 답변 조회 메소드(중복에 따라 update insert 구분하기 위해)
    UserAnswerVO selectUserAnswer(@Param("userId") String userId, @Param("questionId") long questionId);

    // 사용자 총 점수 계산 메서드
    int getTotalScore(@Param("userId") String userId);

    int getTotalAnsweredQuestions(String userId);
}
