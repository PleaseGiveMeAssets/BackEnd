package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedTerms {
    private Long selectedTermsId;
    private Long termsOfUseId;
    private String userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
