package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private String userId;
    private String password;
    private String passwordConfirm;
    private String phoneNumber;
    private String phoneVerificationCode;
    private boolean phoneVerified;
    private String name;
    private String email;
    private String emailVerificationCode;
    private boolean emailVerified;
    private List<TermsAgreementDTO> termsAgreements;
    private boolean idDuplicationChecked;
}
