package com.example.spring.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class SavedNewsVO {
    private int savedNewsId;
    private int newsId;
    private String userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
