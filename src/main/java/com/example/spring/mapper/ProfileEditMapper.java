package com.example.spring.mapper;

import com.example.spring.dto.ProfileEditDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileEditMapper {
    // 특정 사용자 ID로 프로필 정보를 가져옴
    ProfileEditDTO getProfileByMemberId(@Param("memberId") String memberId);

    // 특정 사용자 ID로 프로필 정보를 업데이트함
    void updateProfile(@Param("memberId") String memberId, @Param("profileEditDTO") ProfileEditDTO profileEditDTO);
}