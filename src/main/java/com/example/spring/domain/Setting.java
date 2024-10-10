package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    private Long settingId;
    private String userId;
    private Character modeCode;
    private Character homeNavigationCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
