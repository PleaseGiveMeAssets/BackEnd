package com.example.spring.dto;

import com.example.spring.vo.ExampleVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExampleDTO {
    private String example;

    public static ExampleDTO of(ExampleVO exampleVO) {
        return new ExampleDTO(exampleVO.getExample());
    }
}
