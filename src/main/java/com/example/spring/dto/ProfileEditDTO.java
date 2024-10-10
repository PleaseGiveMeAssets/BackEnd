package com.example.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileEditDTO {

    private String name;           // 이름
    private String nickname;       // 닉네임
    private String phoneFirst;     // 핸드폰 번호 첫 번째 부분
    private String phoneMiddle;    // 핸드폰 번호 중간 부분
    private String phoneLast;      // 핸드폰 번호 마지막 부분
    private String profileImageUrl; // 프로필 이미지 URL
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;        // 생년월일
}
