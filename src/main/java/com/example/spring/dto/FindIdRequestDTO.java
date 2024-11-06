package com.example.spring.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FindIdRequestDTO {
    private String name;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String phoneVerificationCode;
}
