package com.example.spring.mapper;

import com.example.spring.domain.InvestmentTypeAnswer;
import com.example.spring.dto.InvestmentTypeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyResultMapper {

    // 총 점수를 조회하는 메서드 (userId 파라미터 제거)
    int getTotalScore(String userId);

    // 점수에 따른 투자 유형을 조회하는 메서드
    InvestmentTypeDTO getInvestmentTypeByScore(int totalScore);

    // 기존 투자 유형 답변을 조회하는 메서드 (userId 파라미터 제거)
    Long getInvestmentTypeByUserId(String userId);

    // 투자 유형 답변을 삽입하는 메서드
    int insertInvestmentTypeAnswer(InvestmentTypeAnswer newAnswer);

    // 기존 투자 유형 답변을 업데이트하는 메서드
    int updateInvestmentTypeAnswer(InvestmentTypeAnswer existingAnswer);

    // 투자 유형 ID로 해당 투자 유형의 상세 정보를 조회하는 메서드
    InvestmentTypeDTO getInvestmentTypeDetails(Long investmentTypeId);

    String getUserNickname(String userId);
}
