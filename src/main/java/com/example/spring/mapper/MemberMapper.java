package com.example.spring.mapper;

import com.example.spring.domain.Member;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.dto.SocialMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 사용자 프로필 정보와 자산 총액을 조회하는 메서드
    Member selectMemberProfile(@Param("memberId") String memberId);

    int selectMemberById(String memberId);

    int insertMember(MemberDTO memberDTO);

    int insertSocialMember(SocialMemberDTO socialMemberDTO);

    // 아이디찾기
    List<Member> findMemberByNameAndPhone(FindIdRequestDTO findIdRequestDTO);

    Member selectMemberByIdAndNameAndPhone(FindPasswordRequestDTO findPasswordRequestDTO);

    int updatePasswordById(@Param("memberId") String memberId, @Param("password") String password);

    // ID로 사용자 정보를 조회
    Member findByMemberId(@Param("memberId") String memberId);

    // 핸드폰 번호로 사용자 정보 조회
    Member findMemberByPhoneNumber(@Param("phoneFirst") String phoneFirst, @Param("phoneMiddle") String phoneMiddle);

    void incrementPasswordFailureCount(String memberId);

    void resetPasswordFailureCount(String memberId);

    // survey_status 업데이트
    void updateSurveyStatus(@Param("memberId") String memberId, @Param("surveyStatus") char surveyStatus);

    // survey_status 조회
    String getSurveyStatus(@Param("memberId") String memberId);
}
