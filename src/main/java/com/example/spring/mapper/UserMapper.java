package com.example.spring.mapper;

import com.example.spring.domain.User;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 사용자 프로필 정보와 자산 총액을 조회하는 메서드
    User selectUserProfile(@Param("userId") String userId);

    int selectUserById(String userId);

    int insertUser(MemberDTO memberDTO);

    // 아이디찾기
    List<User> findMemberByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    User selectUserByIdAndNameAndPhone(FindPasswordRequestDTO findPasswordRequestDTO);

    int updatePasswordById(@Param("userId") String userId, @Param("password") String password);
}
