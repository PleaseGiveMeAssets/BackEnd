package com.example.spring.mapper;

import com.example.spring.domain.InvestmentTypeAnswer;
import com.example.spring.dto.InvestmentTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SurveyResultMapper {
    // 총 점수 계산 메서드
    int getTotalScore(@Param("userId") String userId);

    // 점수에 따른 투자 유형 조회 메서드
    InvestmentTypeDTO getInvestmentTypeByScore(@Param("totalScore") int totalScore);

    // USER_ID로 기존 투자 유형 답변 조회 메서드
    InvestmentTypeAnswer findInvestmentTypeAnswerByUserId(@Param("userId") String userId);

    // 투자 유형 답변을 삽입하는 메서드
    int insertInvestmentTypeAnswer(InvestmentTypeAnswer newAnswer);

    // 투자 유형 답변을 업데이트하는 메서드
    int updateInvestmentTypeAnswer(InvestmentTypeAnswer existingAnswer);

    // 사용자 ID로 투자 유형 ID 가져오기
    Long getInvestmentTypeIdByUserId(@Param("userId") String userId);

    // 투자 유형 ID로 투자 유형 상세 정보 가져오기
    InvestmentTypeDTO getInvestmentTypeDetails(@Param("investmentTypeId") Long investmentTypeId);
}
