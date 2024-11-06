package com.example.spring.service;

import com.example.spring.domain.InvestmentTypeAnswer;
import com.example.spring.dto.InvestmentTypeDTO;
import com.example.spring.dto.InvestmentTypeAnswerDTO;
import com.example.spring.exception.*;
import com.example.spring.mapper.SurveyResultMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Slf4j
public class SurveyResultServiceImpl implements SurveyResultService {

    private final SurveyResultMapper surveyResultMapper;

    @Autowired
    public SurveyResultServiceImpl(SurveyResultMapper surveyResultMapper) {
        this.surveyResultMapper = surveyResultMapper;
    }

    // JWT 토큰에서 memberId를 추출하는 메서드
    private String getMemberIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // JWT 토큰에서 memberId 추출
    }

    @Override
    public int getTotalScore() {
        String memberId = getMemberIdFromToken();  // JWT 토큰에서 memberId 추출
        try {
            int totalScore = surveyResultMapper.getTotalScore(memberId);
            log.info("Total score for memberId {}: {}", memberId, totalScore);
            return totalScore;
        } catch (Exception e) {
            log.error("Error while calculating total score for memberId: {}", memberId, e);
            throw new TotalScoreCalculationException("Failed to calculate total score for memberId: " + memberId, e);
        }
    }

    @Override
    @Transactional
    public InvestmentTypeAnswerDTO getInvestmentTypeByMemberId() {
        String memberId = getMemberIdFromToken();  // JWT 토큰에서 memberId 추출
        try {
            // 총 점수 계산
            int totalScore = getTotalScore();

            // 점수에 따른 투자 유형 조회
            InvestmentTypeDTO investmentType = surveyResultMapper.getInvestmentTypeByScore(totalScore);

            if (investmentType == null) {
                log.warn("Investment type not found for total score: {}", totalScore);
                throw new ResourceNotFoundException("Investment type not found for total score: " + totalScore);
            }

            // 투자 유형 답변 저장 또는 업데이트
            insertOrUpdateInvestmentTypeAnswer(investmentType.getInvestmentTypeId());

            // InvestmentTypeAnswerDTO로 반환
            InvestmentTypeAnswerDTO answerDTO = new InvestmentTypeAnswerDTO();
            answerDTO.setMemberId(memberId);
            answerDTO.setInvestmentTypeId(investmentType.getInvestmentTypeId());

            log.info("Investment type answer for memberId {}: {}", memberId, investmentType.getInvestmentTypeId());
            return answerDTO;
        } catch (Exception e) {
            log.error("Error while retrieving investment type answer for memberId: {}", memberId, e);
            throw new InvestmentTypeAnswerProcessingException("Failed to get investment type answer for memberId: " + memberId, e);
        }
    }

    @Override
    public void insertOrUpdateInvestmentTypeAnswer(Long investmentTypeId) {
        String memberId = getMemberIdFromToken();  // JWT 토큰에서 memberId 추출
        try {
            // 기존 답변이 있는지 확인
            Long existingInvestmentTypeId = surveyResultMapper.getInvestmentTypeByMemberId(memberId);

            if (existingInvestmentTypeId != null) {
                // 기존 답변이 있을 경우 updatedAt만 수정
                InvestmentTypeAnswer existingAnswer = new InvestmentTypeAnswer(memberId, investmentTypeId, null, new Timestamp(System.currentTimeMillis()));
                int result = surveyResultMapper.updateInvestmentTypeAnswer(existingAnswer);

                if (result <= 0) {
                    log.error("Failed to update InvestmentTypeAnswer for memberId: {}", memberId);
                    throw new InvestmentTypeAnswerProcessingException("Failed to update investment type answer for memberId: " + memberId);
                }

                log.info("Successfully updated InvestmentTypeAnswer for memberId: {}", memberId);
            } else {
                // 기존 답변이 없을 경우 createdAt만 설정하여 새로 삽입
                InvestmentTypeAnswer newAnswer = new InvestmentTypeAnswer(memberId, investmentTypeId, new Timestamp(System.currentTimeMillis()), null);
                int result = surveyResultMapper.insertInvestmentTypeAnswer(newAnswer);

                if (result <= 0) {
                    log.error("Failed to insert InvestmentTypeAnswer for memberId: {}", memberId);
                    throw new InvestmentTypeAnswerProcessingException("Failed to insert investment type answer for memberId: " + memberId);
                }

                log.info("Successfully inserted InvestmentTypeAnswer for memberId: {}", memberId);
            }

        } catch (Exception e) {
            log.error("Error while saving investment type answer for memberId: {}", memberId, e);
            throw new InvestmentTypeAnswerProcessingException("An error occurred while saving the investment type answer for memberId: " + memberId, e);
        }
    }

    @Override
    public InvestmentTypeDTO getInvestmentTypeDetails() {
        String memberId = getMemberIdFromToken();  // JWT 토큰에서 memberId 추출
        // Long 타입의 투자 유형 ID 조회
        Long investmentTypeId = surveyResultMapper.getInvestmentTypeByMemberId(memberId);

        if (investmentTypeId == null) {
            log.info("Investment type ID not found for memberId: {}", memberId);
            throw new ResourceNotFoundException("Investment type not found for memberId: " + memberId);
        }

        // 투자 유형 상세 정보 조회
        InvestmentTypeDTO investmentTypeDTO = surveyResultMapper.getInvestmentTypeDetails(investmentTypeId);

        if (investmentTypeDTO == null) {
            log.info("Investment type details not found for investmentTypeId: {}", investmentTypeId);
            throw new ResourceNotFoundException("Investment type details not found for ID: " + investmentTypeId);
        }

        log.info("Investment type details for memberId {}: {}", memberId, investmentTypeDTO.getInvestmentTypeName());
        return investmentTypeDTO;
    }

    @Override
    public String getMemberNickname(String memberId) {
        try {
            String nickname = surveyResultMapper.getMemberNickname(memberId); // Mapper를 통해 닉네임 조회
            log.info("Member nickname for memberId {}: {}", memberId, nickname);
            return nickname;
        } catch (Exception e) {
            log.error("Error while retrieving nickname for memberId: {}", memberId, e);
            throw new RuntimeException("Failed to get nickname for memberId: " + memberId);
        }
    }
}
