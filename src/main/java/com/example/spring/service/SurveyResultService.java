package com.example.spring.service;

import com.example.spring.dto.InvestmentTypeAnswerDTO;
import com.example.spring.dto.InvestmentTypeDTO;

public interface SurveyResultService {

    // 사용자의 총 점수를 계산
    int getTotalScore(); // userId 파라미터 제거

    // InvestmentTypeAnswer 테이블에 투자 유형을 저장 또는 업데이트
    void insertOrUpdateInvestmentTypeAnswer(Long investmentTypeId);

    // 현재 로그인된 사용자의 투자 유형을 반환하는 메서드
    InvestmentTypeAnswerDTO getInvestmentTypeByUserId(); // userId 파라미터 제거

    // 사용자의 투자유형 ID로 INVESTMENT_TYPE 테이블에서 상세 정보를 조회
    InvestmentTypeDTO getInvestmentTypeDetails(); // userId 파라미터 제거

    String getUserNickname(String userId);
    

}
