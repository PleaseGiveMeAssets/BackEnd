package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindIdRequestDTO {
    private String name;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String phoneVerificationCode;
}
