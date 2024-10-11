package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMemberDTO {
    private String userId;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String name;
    private String email;
    private String sns;
}
