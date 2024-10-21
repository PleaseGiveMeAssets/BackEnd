package com.example.spring.mapper;

import com.example.spring.domain.User;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.dto.SocialMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 사용자 프로필 정보와 자산 총액을 조회하는 메서드
    User selectUserProfile(@Param("userId") String userId);

    int selectUserById(String userId);

    int insertUser(MemberDTO memberDTO);

    int insertSocialUser(SocialMemberDTO socialMemberDTO);

    // 아이디찾기
    List<User> findMemberByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    List<User> selectUserByIdAndNameAndPhone(FindPasswordRequestDTO findPasswordRequestDTO);

    int updatePasswordById(@Param("userId") String userId, @Param("password") String password);

    // ID로 사용자 정보를 조회
    User findByUserId(@Param("userId") String userId);

    // 핸드폰 번호로 사용자 정보 조회
    User findMemberByPhoneNumber(@Param("phoneFirst") String phoneFirst, @Param("phoneMiddle") String phoneMiddle);

    void incrementPasswordFailureCount(String userId);

    void resetPasswordFailureCount(String userId);

    // survey_status 업데이트
    void updateSurveyStatus(@Param("userId") String userId, @Param("surveyStatus") char surveyStatus);

    // survey_status 조회
    String getSurveyStatus(@Param("userId") String userId);
}
