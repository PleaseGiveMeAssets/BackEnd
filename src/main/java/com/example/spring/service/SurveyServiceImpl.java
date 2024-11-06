package com.example.spring.service;

import com.example.spring.domain.Question;
import com.example.spring.domain.QuestionOption;
import com.example.spring.dto.QuestionDTO;
import com.example.spring.dto.QuestionOptionDTO;
import com.example.spring.dto.MemberAnswerDTO;
import com.example.spring.exception.ResourceNotFoundException;
import com.example.spring.exception.MemberAnswerProcessingException;
import com.example.spring.mapper.QuestionMapper;
import com.example.spring.mapper.MemberAnswerMapper;
import com.example.spring.mapper.MemberMapper;
import com.example.spring.vo.MemberAnswerVO;
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
    private final MemberAnswerMapper memberAnswerMapper;
    private final MemberMapper memberMapper;

    @Autowired
    public SurveyServiceImpl(MemberMapper memberMapper,QuestionMapper questionMapper, MemberAnswerMapper memberAnswerMapper) {
        this.questionMapper = questionMapper;
        this.memberAnswerMapper = memberAnswerMapper;
        this.memberMapper = memberMapper;
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
    public void insertOrUpdateMemberAnswer(String memberId, long questionId, MemberAnswerDTO memberAnswerDTO) {
        try {
            // 기존 답변 확인
            MemberAnswerVO existingAnswer = memberAnswerMapper.selectMemberAnswer(memberId, questionId);

            // MemberAnswerDTO -> MemberAnswerVO 변환
            MemberAnswerVO memberAnswerVO = new MemberAnswerVO();
            memberAnswerVO.setMemberId(memberId);
            memberAnswerVO.setQuestionId(questionId);
            memberAnswerVO.setQuestionOptionId(memberAnswerDTO.getOptionId());

            if (existingAnswer == null) {
                // 새 답변 삽입
                int insertResult = memberAnswerMapper.insertMemberAnswer(memberAnswerVO);
                if (insertResult <= 0) {
                    log.error("Failed to insert answer for memberId: {}, questionId: {}", memberId, questionId);
                    throw new MemberAnswerProcessingException("Failed to insert answer for memberId: " + memberId);
                }
                log.info("New answer inserted for memberId: {}, questionId: {}, optionId: {}", memberId, questionId, memberAnswerDTO.getOptionId());
            } else {
                // 기존 답변 업데이트
                int updateResult = memberAnswerMapper.updateMemberAnswer(memberAnswerVO);
                if (updateResult <= 0) {
                    log.error("Failed to update answer for memberId: {}, questionId: {}", memberId, questionId);
                    throw new MemberAnswerProcessingException("Failed to update answer for memberId: " + memberId);
                }
                log.info("Answer updated for memberId: {}, questionId: {}, optionId: {}", memberId, questionId, memberAnswerDTO.getOptionId());
            }

            // 모든 질문에 대한 답변이 완료되었는지 확인
            int totalQuestions = questionMapper.getTotalQuestionsCount(); // 전체 질문 수
            int answeredQuestions = memberAnswerMapper.getTotalAnsweredQuestions(memberId); // 답변한 질문 수

            if (answeredQuestions == totalQuestions) {
                // 모든 질문이 완료되었으면 설문 상태를 'Y'로 업데이트
                updateSurveyStatus(memberId, 'Y');
            }


        } catch (Exception e) {
            log.error("Error while inserting/updating member answer for memberId: {}, questionId: {}", memberId, questionId, e);
            throw new MemberAnswerProcessingException("Error while inserting/updating member answer");
        }
    }

    @Override
    public void updateSurveyStatus(String memberId, char surveyStatus) {
        // 설문 상태를 업데이트
        memberMapper.updateSurveyStatus(memberId, surveyStatus);
        log.info("Survey status updated for memberId: {}, new status: {}", memberId, surveyStatus);
    }

    @Override
    public String getSurveyStatus(String memberId) {
        // 사용자 설문 상태 조회
        return memberMapper.getSurveyStatus(memberId);
    }

    @Override
    public boolean areAllQuestionsAnswered(String memberId) {
        // 전체 질문 수와 답변한 질문 수를 비교
        int totalQuestions = questionMapper.getTotalQuestionsCount();
        int answeredQuestions = memberAnswerMapper.getTotalAnsweredQuestions(memberId);

        return answeredQuestions == totalQuestions;
    }


}
