package com.example.spring.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserVO {
    private final String userId;
    private final String profileImageUrl;
    private final String nickname;

}
