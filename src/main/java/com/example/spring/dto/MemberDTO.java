package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
