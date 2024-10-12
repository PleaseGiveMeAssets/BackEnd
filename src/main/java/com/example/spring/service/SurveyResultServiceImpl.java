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

    // JWT 토큰에서 userId를 추출하는 메서드
    private String getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();  // JWT 토큰에서 userId 추출
    }

    @Override
    public int getTotalScore() {
        String userId = getUserIdFromToken();  // JWT 토큰에서 userId 추출
        try {
            int totalScore = surveyResultMapper.getTotalScore(userId);
            log.info("Total score for userId {}: {}", userId, totalScore);
            return totalScore;
        } catch (Exception e) {
            log.error("Error while calculating total score for userId: {}", userId, e);
            throw new TotalScoreCalculationException("Failed to calculate total score for userId: " + userId, e);
        }
    }

    @Override
    @Transactional
    public InvestmentTypeAnswerDTO getInvestmentTypeByUserId() {
        String userId = getUserIdFromToken();  // JWT 토큰에서 userId 추출
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
            answerDTO.setUserId(userId);
            answerDTO.setInvestmentTypeId(investmentType.getInvestmentTypeId());

            log.info("Investment type answer for userId {}: {}", userId, investmentType.getInvestmentTypeId());
            return answerDTO;
        } catch (Exception e) {
            log.error("Error while retrieving investment type answer for userId: {}", userId, e);
            throw new InvestmentTypeAnswerProcessingException("Failed to get investment type answer for userId: " + userId, e);
        }
    }

    @Override
    public void insertOrUpdateInvestmentTypeAnswer(Long investmentTypeId) {
        String userId = getUserIdFromToken();  // JWT 토큰에서 userId 추출
        try {
            // 기존 답변이 있는지 확인
            Long existingInvestmentTypeId = surveyResultMapper.getInvestmentTypeByUserId(userId);

            if (existingInvestmentTypeId != null) {
                // 기존 답변이 있을 경우 updatedAt만 수정
                InvestmentTypeAnswer existingAnswer = new InvestmentTypeAnswer(userId, investmentTypeId, null, new Timestamp(System.currentTimeMillis()));
                int result = surveyResultMapper.updateInvestmentTypeAnswer(existingAnswer);

                if (result <= 0) {
                    log.error("Failed to update InvestmentTypeAnswer for userId: {}", userId);
                    throw new InvestmentTypeAnswerProcessingException("Failed to update investment type answer for userId: " + userId);
                }

                log.info("Successfully updated InvestmentTypeAnswer for userId: {}", userId);
            } else {
                // 기존 답변이 없을 경우 createdAt만 설정하여 새로 삽입
                InvestmentTypeAnswer newAnswer = new InvestmentTypeAnswer(userId, investmentTypeId, new Timestamp(System.currentTimeMillis()), null);
                int result = surveyResultMapper.insertInvestmentTypeAnswer(newAnswer);

                if (result <= 0) {
                    log.error("Failed to insert InvestmentTypeAnswer for userId: {}", userId);
                    throw new InvestmentTypeAnswerProcessingException("Failed to insert investment type answer for userId: " + userId);
                }

                log.info("Successfully inserted InvestmentTypeAnswer for userId: {}", userId);
            }

        } catch (Exception e) {
            log.error("Error while saving investment type answer for userId: {}", userId, e);
            throw new InvestmentTypeAnswerProcessingException("An error occurred while saving the investment type answer for userId: " + userId, e);
        }
    }

    @Override
    public InvestmentTypeDTO getInvestmentTypeDetails() {
        String userId = getUserIdFromToken();  // JWT 토큰에서 userId 추출
        // Long 타입의 투자 유형 ID 조회
        Long investmentTypeId = surveyResultMapper.getInvestmentTypeByUserId(userId);

        if (investmentTypeId == null) {
            log.info("Investment type ID not found for userId: {}", userId);
            throw new ResourceNotFoundException("Investment type not found for userId: " + userId);
        }

        // 투자 유형 상세 정보 조회
        InvestmentTypeDTO investmentTypeDTO = surveyResultMapper.getInvestmentTypeDetails(investmentTypeId);

        if (investmentTypeDTO == null) {
            log.info("Investment type details not found for investmentTypeId: {}", investmentTypeId);
            throw new ResourceNotFoundException("Investment type details not found for ID: " + investmentTypeId);
        }

        log.info("Investment type details for userId {}: {}", userId, investmentTypeDTO.getInvestmentTypeName());
        return investmentTypeDTO;
    }

    @Override
    public String getUserNickname(String userId) {
        try {
            String nickname = surveyResultMapper.getUserNickname(userId); // Mapper를 통해 닉네임 조회
            log.info("User nickname for userId {}: {}", userId, nickname);
            return nickname;
        } catch (Exception e) {
            log.error("Error while retrieving nickname for userId: {}", userId, e);
            throw new RuntimeException("Failed to get nickname for userId: " + userId);
        }
    }
}
