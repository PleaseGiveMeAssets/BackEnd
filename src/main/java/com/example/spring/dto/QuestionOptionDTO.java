package com.example.spring.dto;

import com.example.spring.vo.QuestionOptionVO;
import lombok.Data;

@Data
public class QuestionOptionDTO {
    private int questionOptionId;
    private String optionContent;
    private double score;

    // QuestionOptionVO에서 DTO로 변환하는 메서드 추가
    public static QuestionOptionDTO fromVO(QuestionOptionVO optionVO) {
        QuestionOptionDTO dto = new QuestionOptionDTO();
        dto.setQuestionOptionId(optionVO.getQuestionOptionId());
        dto.setOptionContent(optionVO.getContent());
        dto.setScore(optionVO.getScore());
        return dto;
    }

}