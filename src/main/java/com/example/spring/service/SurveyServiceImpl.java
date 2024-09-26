package com.example.spring.service;

import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.QuestionOptionDTO;
import com.example.spring.mapper.QuestionMapper;
import com.example.spring.vo.QuestionOptionVO;
import com.example.spring.vo.QuestionVO;
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

    @Autowired
    public SurveyServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public QuestionDTO getSurveyQuestion(int questionId) {
        // 데이터베이스에서 설문조사 질문을 가져옴
        QuestionVO questionVO = questionMapper.selectSurveyQuestion(questionId);

        if (questionVO == null) {
            log.warn("Question not found for questionId: {}", questionId);
            return null;
        }

        //질문에 해당하는 옵션 정보 가져오기
        List<QuestionOptionVO> optionVOs = questionMapper.selectOptionsByQuestionId(questionId);

        // QuestionVO -> QuestionDTO 변환
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(questionVO, questionDTO);

        // QuestionOptionVO -> QuestionOptionDTO 변환 후 DTO에 추가
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
}
