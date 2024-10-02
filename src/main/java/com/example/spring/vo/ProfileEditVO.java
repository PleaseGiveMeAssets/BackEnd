package com.example.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileEditVO {

    private String name;
    private String nickname;
    private String phoneFirst;
    private String phoneMiddle;
    private String phoneLast;
    private String profileImageUrl;
    private Date birthDate;
}
