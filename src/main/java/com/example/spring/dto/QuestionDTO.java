package com.example.spring.dto;

import com.example.spring.vo.QuestionOptionVO;
import com.example.spring.vo.QuestionVO;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {
    private int questionId;
    private String content;
    private List<QuestionOptionDTO> options; // 클라이언트에 보낼 데이터만 포함

    // 이 DTO로 변환하는 별도의 메서드 추가 가능
    public static QuestionDTO fromVO(QuestionVO questionVO, List<QuestionOptionVO> optionVOs) {
        QuestionDTO dto = new QuestionDTO();
        dto.setQuestionId(questionVO.getQuestionId());
        dto.setContent(questionVO.getContent());

        // 각 OptionVO를 OptionDTO로 변환
        List<QuestionOptionDTO> optionDTOs = optionVOs.stream()
                .map(QuestionOptionDTO::fromVO)
                .collect(Collectors.toList());

        dto.setOptions(optionDTOs);
        return dto;
    }

}
