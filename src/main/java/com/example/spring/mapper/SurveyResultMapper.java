package com.example.spring.mapper;

import com.example.spring.dto.InvestmentTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SurveyResultMapper {
    // 총 점수 계산 메서드
    int getTotalScore(@Param("userId") String userId);

    // 점수에 따른 투자 유형 조회 메서드
    InvestmentTypeDTO getInvestmentTypeByScore(@Param("totalScore") int totalScore);



}
