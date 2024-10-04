package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String userId;
    private String password;
    private String passwordConfirmation;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String phoneVerificationCode;
    private String name;
    private String email;
    private String emailVerificationCode;
    private List<TermsAgreementDTO> termsAgreementDTOList;
}
