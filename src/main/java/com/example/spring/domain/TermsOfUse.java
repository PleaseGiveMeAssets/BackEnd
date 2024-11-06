package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TermsOfUse {
    private Long termsOfUseId;
    private String title;
    private String content;
    private Character required;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private SelectedTerms selectedTerms;
}
