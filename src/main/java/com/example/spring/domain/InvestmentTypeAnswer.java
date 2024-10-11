package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentTypeAnswer {

    private String userId;
    private Long investmentTypeId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
