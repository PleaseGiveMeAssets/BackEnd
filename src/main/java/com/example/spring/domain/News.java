package com.example.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private Long newsId;
    private Long stockId;
    private String title;
    private String content;
    private String link;
    private String image;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<SavedNews> savedNewsList;
}
