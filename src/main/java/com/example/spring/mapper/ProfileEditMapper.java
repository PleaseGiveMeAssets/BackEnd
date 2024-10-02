package com.example.spring.mapper;

import com.example.spring.vo.ProfileEditVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileEditMapper {
    // 특정 사용자 ID로 프로필 정보를 가져옴
    ProfileEditVO getProfileByUserId(@Param("userId") String userId);

    // 특정 사용자 ID로 프로필 정보를 업데이트함
    void updateProfile(@Param("userId") String userId, @Param("profileEditVO")  ProfileEditVO profileEditVO);
}
