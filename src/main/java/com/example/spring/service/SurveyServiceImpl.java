package com.example.spring.service;

import com.example.spring.domain.Question;
import com.example.spring.domain.QuestionOption;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.QuestionOptionDTO;
import com.example.spring.dto.UserAnswerDTO;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.exception.UserAnswerProcessingException;
import com.example.spring.mapper.QuestionMapper;
import com.example.spring.mapper.UserAnswerMapper;
import com.example.spring.mapper.UserMapper;
import com.example.spring.vo.UserAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    private final QuestionMapper questionMapper;
    private final UserAnswerMapper userAnswerMapper;
    private final UserMapper userMapper;

    @Autowired
    public SurveyServiceImpl(UserMapper userMapper,QuestionMapper questionMapper, UserAnswerMapper userAnswerMapper) {
        this.questionMapper = questionMapper;
        this.userAnswerMapper = userAnswerMapper;
        this.userMapper = userMapper;
    }

    @Override
    public QuestionDTO getSurveyQuestion(long questionId) {
        // 설문 질문 가져오기
        Question question = questionMapper.selectSurveyQuestion(questionId);

        if (question == null) {
            log.error("Question not found for questionId: {}", questionId);
            throw new ResourceNotFoundException("Question not found for questionId: " + questionId);
        }

        // Question -> QuestionDTO로 변환 (필드 직접 설정)
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(question.getQuestionId());
        questionDTO.setContent(question.getContent());

        // 질문 옵션 가져오기 및 DTO로 변환
        List<QuestionOption> options = questionMapper.selectOptionsByQuestionId(questionId);
        List<QuestionOptionDTO> optionDTOList = new ArrayList<>(options.size()); // 옵션 리스트 크기 미리 설정

        for (QuestionOption option : options) {
            QuestionOptionDTO questionOptionDTO = new QuestionOptionDTO();
            questionOptionDTO.setQuestionOptionId(option.getQuestionOptionId());
            questionOptionDTO.setContent(option.getContent());
            questionOptionDTO.setScore(option.getScore());
            optionDTOList.add(questionOptionDTO);
        }

        questionDTO.setOptions(optionDTOList); // 변환된 옵션 리스트 설정
        log.info("Survey question retrieved: {}", questionDTO);

        return questionDTO;
    }

    @Override
    public void insertOrUpdateUserAnswer(String userId, long questionId, UserAnswerDTO userAnswerDTO) {
        try {
            // 기존 답변 확인
            UserAnswerVO existingAnswer = userAnswerMapper.selectUserAnswer(userId, questionId);

            // UserAnswerDTO -> UserAnswerVO 변환
            UserAnswerVO userAnswerVO = new UserAnswerVO();
            userAnswerVO.setUserId(userId);
            userAnswerVO.setQuestionId(questionId);
            userAnswerVO.setQuestionOptionId(userAnswerDTO.getOptionId());

            if (existingAnswer == null) {
                // 새 답변 삽입
                int insertResult = userAnswerMapper.insertUserAnswer(userAnswerVO);
                if (insertResult <= 0) {
                    log.error("Failed to insert answer for userId: {}, questionId: {}", userId, questionId);
                    throw new UserAnswerProcessingException("Failed to insert answer for userId: " + userId);
                }
                log.info("New answer inserted for userId: {}, questionId: {}, optionId: {}", userId, questionId, userAnswerDTO.getOptionId());
            } else {
                // 기존 답변 업데이트
                int updateResult = userAnswerMapper.updateUserAnswer(userAnswerVO);
                if (updateResult <= 0) {
                    log.error("Failed to update answer for userId: {}, questionId: {}", userId, questionId);
                    throw new UserAnswerProcessingException("Failed to update answer for userId: " + userId);
                }
                log.info("Answer updated for userId: {}, questionId: {}, optionId: {}", userId, questionId, userAnswerDTO.getOptionId());
            }

            // 모든 질문에 대한 답변이 완료되었는지 확인
            int totalQuestions = questionMapper.getTotalQuestionsCount(); // 전체 질문 수
            int answeredQuestions = userAnswerMapper.getTotalAnsweredQuestions(userId); // 답변한 질문 수

            if (answeredQuestions == totalQuestions) {
                // 모든 질문이 완료되었으면 설문 상태를 'Y'로 업데이트
                updateSurveyStatus(userId, 'Y');
            }


        } catch (Exception e) {
            log.error("Error while inserting/updating user answer for userId: {}, questionId: {}", userId, questionId, e);
            throw new UserAnswerProcessingException("Error while inserting/updating user answer");
        }
    }

    @Override
    public void updateSurveyStatus(String userId, char surveyStatus) {
        // 설문 상태를 업데이트
        userMapper.updateSurveyStatus(userId, surveyStatus);
        log.info("Survey status updated for userId: {}, new status: {}", userId, surveyStatus);
    }

    @Override
    public String getSurveyStatus(String userId) {
        // 사용자 설문 상태 조회
        return userMapper.getSurveyStatus(userId);
    }

    @Override
    public boolean areAllQuestionsAnswered(String userId) {
        // 전체 질문 수와 답변한 질문 수를 비교
        int totalQuestions = questionMapper.getTotalQuestionsCount();
        int answeredQuestions = userAnswerMapper.getTotalAnsweredQuestions(userId);

        return answeredQuestions == totalQuestions;
    }


}
