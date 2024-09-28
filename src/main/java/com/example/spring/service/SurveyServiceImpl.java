package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.QuestionOptionDTO;
import com.example.spring.dto.UserAnswerDTO;
import com.example.spring.mapper.QuestionMapper;
import com.example.spring.mapper.UserAnswerMapper;
import com.example.spring.vo.QuestionOptionVO;
import com.example.spring.vo.QuestionVO;
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
        QuestionVO questionVO = questionMapper.selectSurveyQuestion(questionId);

        if (questionVO == null) {
            log.warn("Question not found for questionId: {}", questionId);
            return null;
        }

        List<QuestionOptionVO> optionVOs = questionMapper.selectOptionsByQuestionId(questionId);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionVO, questionDTO);

        List<QuestionOptionDTO> optionDTOs = optionVOs.stream()
                .map(optionVO -> {
                    QuestionOptionDTO optionDTO = new QuestionOptionDTO();
                    BeanUtils.copyProperties(optionVO, optionDTO);
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

        if (existingAnswer == null) {
            // 신규 답변 삽입
            UserAnswerVO userAnswerVO = new UserAnswerVO();
            userAnswerVO.setUserId(userId);
            userAnswerVO.setQuestionId(questionId);
            userAnswerVO.setQuestionOptionId(userAnswerDTO.getOptionId());
            return userAnswerMapper.insertUserAnswer(userAnswerVO);
        } else {
            // 기존 답변 업데이트
            UserAnswerVO userAnswerVO = new UserAnswerVO();
            userAnswerVO.setUserId(userId);
            userAnswerVO.setQuestionId(questionId);
            userAnswerVO.setQuestionOptionId(userAnswerDTO.getOptionId());
            return userAnswerMapper.updateUserAnswer(userAnswerVO);
        }
    } catch (Exception e) {
        log.error("Error while inserting/updating user answer", e);
        return -1; // 에러 코드 반환
    }
    }
}

