package com.example.spring.mapper;

import com.example.spring.domain.MemberSms;
import com.example.spring.dto.SmsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberSmsMapper {
    List<MemberSms> select(SmsDTO smsDTO);

    int insert(SmsDTO smsDTO);

    int updatePhoneVerification(SmsDTO smsDTO);

    int updatePhoneVerificationStatus(SmsDTO smsDTO);
}
