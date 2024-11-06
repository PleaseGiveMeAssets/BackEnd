package com.example.spring.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TermsAgreementDTO {
    private Long termsOfUseId;
    private Character required;
}