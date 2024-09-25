package com.example.spring.mapper;

import com.example.spring.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 사용자 프로필 정보와 자산 총액을 조회하는 메서드
    UserVO selectUserProfile(@Param("userId") String userId);
}
