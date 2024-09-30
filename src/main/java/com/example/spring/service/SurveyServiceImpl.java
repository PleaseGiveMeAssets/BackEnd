package com.example.spring.service;

import com.example.spring.domain.Question;
import com.example.spring.domain.QuestionOption;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.QuestionOptionDTO;
import com.example.spring.dto.UserAnswerDTO;
import com.example.spring.mapper.QuestionMapper;
import com.example.spring.mapper.UserAnswerMapper;
import com.example.spring.vo.UserAnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SurveyServiceImpl implements SurveyService {

    private final QuestionMapper questionMapper;
    private final UserAnswerMapper userAnswerMapper;

    @Autowired
    public SurveyServiceImpl(QuestionMapper questionMapper, UserAnswerMapper userAnswerMapper) {
        this.questionMapper = questionMapper;
        this.userAnswerMapper = userAnswerMapper;
    }

    @Override
    public QuestionDTO getSurveyQuestion(int questionId) {
        Question question = questionMapper.selectSurveyQuestion(questionId);

        if (question == null) {
            log.warn("Question not found for questionId: {}", questionId);
            return null;
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        List<QuestionOption> options = questionMapper.selectOptionsByQuestionId(questionId);
        List<QuestionOptionDTO> optionDTOs = options.stream()
                .map(option -> {
                    QuestionOptionDTO optionDTO = new QuestionOptionDTO();
                    BeanUtils.copyProperties(option, optionDTO);
                    return optionDTO;
                })
                .collect(Collectors.toList());

        questionDTO.setOptions(optionDTOs);
        log.info("Survey question retrieved: {}", questionDTO);
        return questionDTO;
    }

    @Override
    public int insertOrUpdateUserAnswer(String userId, int questionId, UserAnswerDTO userAnswerDTO) {
        try {
            UserAnswerVO existingAnswer = userAnswerMapper.selectUserAnswer(userId, questionId);

            UserAnswerVO userAnswerVO = new UserAnswerVO();
            userAnswerVO.setUserId(userId);
            userAnswerVO.setQuestionId(questionId);
            userAnswerVO.setQuestionOptionId(userAnswerDTO.getOptionId());

            if (existingAnswer == null) {
                // 신규 답변 삽입
                int insertResult = userAnswerMapper.insertUserAnswer(userAnswerVO);
                if (insertResult > 0) {
//                    log.info("New answer inserted for userId: {}, questionId: {}, optionId: {}", userId, questionId, userAnswerDTO.getOptionId());
                    log.info("New answer inserted : {}", userAnswerDTO);
                }
                return insertResult;
            } else {
                // 기존 답변 업데이트
                int updateResult = userAnswerMapper.updateUserAnswer(userAnswerVO);
                if (updateResult > 0) {
//                    log.info("Answer updated for userId: {}, questionId: {}, optionId: {}", userId, questionId, userAnswerDTO.getOptionId());
                    log.info("Answer updated : {}", userAnswerDTO);
                }
                return updateResult;
            }
        } catch (Exception e) {
            log.error("Error while inserting/updating user answer for userId: {}, questionId: {}", userId, questionId, e);
            return -1; // 에러 코드 반환
        }
    }
}

