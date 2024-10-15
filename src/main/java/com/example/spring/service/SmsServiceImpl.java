package com.example.spring.service;

import com.example.spring.domain.MemberSms;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.dto.SmsDTO;
import com.example.spring.exception.InvalidVerificationCodeException;
import com.example.spring.mapper.MemberSmsMapper;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    @Value("${coolsms.api-key}")
    private String apiKey;

    @Value("${coolsms.api-secret}")
    private String apiSecret;

    private final EncryptionService encryptionService;
    private final MemberSmsMapper memberSmsMapper;

    @Autowired
    public SmsServiceImpl(EncryptionService encryptionService, MemberSmsMapper memberSmsMapper) {
        this.encryptionService = encryptionService;
        this.memberSmsMapper = memberSmsMapper;
    }

    @Override
    public SmsDTO sendSms(SmsDTO smsDTO) {
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
        // Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요

        // 인증코드
        String phoneVerificationCode = generateVerificationCode();

        Message message = new Message();
        message.setFrom(MemberCodeEnum.SEND_NUMBER.getValue());
        message.setTo(smsDTO.getPhoneFirst() + smsDTO.getPhoneMiddle() + smsDTO.getPhoneLast());
        message.setText("[너굴] 아래의 인증번호를 입력해주세요\n" + phoneVerificationCode);

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(message);

            List<MemberSms> memberSmsList = memberSmsMapper.select(smsDTO);

            if (log.isInfoEnabled()) {
                log.info("sendSms smsDTO : {}", smsDTO.toString());
            }

            if (memberSmsList.isEmpty()) {
                smsDTO.setPhoneLast(encryptionService.encrypt(smsDTO.getPhoneLast()));
                smsDTO.setPhoneVerificationCode(phoneVerificationCode);
                memberSmsMapper.insert(smsDTO);
            } else {
                for (MemberSms memberSms : memberSmsList) {
                    if (smsDTO.getPhoneLast().equals(encryptionService.decrypt(memberSms.getPhoneLast()))) {
                        smsDTO.setPhoneLast(memberSms.getPhoneLast());
                        smsDTO.setPhoneVerificationCode(phoneVerificationCode);
                        memberSmsMapper.updatePhoneVerification(smsDTO);
                        break;
                    }
                }
            }
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return smsDTO;
    }

    @Override
    public int checkSmsVerification(SmsDTO smsDTO) {
        List<MemberSms> memberSmsList = memberSmsMapper.select(smsDTO);

        if (log.isInfoEnabled()) {
            log.info("checkSmsVerification memberSmsList : {}", memberSmsList.toString());
        }

        int checkMemberSmsCount = 0;

        if (memberSmsList.isEmpty()) {
            throw new InvalidVerificationCodeException(ResultCodeEnum.INVALID_VERIFICATION_CODE.getMessage());
        } else {
            boolean isMatched = false;

            for (MemberSms memberSms : memberSmsList) {
                if (smsDTO.getPhoneLast().equals(encryptionService.decrypt(memberSms.getPhoneLast()))) {
                    smsDTO.setPhoneLast(memberSms.getPhoneLast());
                    checkMemberSmsCount = memberSmsMapper.updatePhoneVerificationStatus(smsDTO);
                    isMatched = true;
                    break;
                }
            }

            if (!isMatched) {
                throw new InvalidVerificationCodeException(ResultCodeEnum.INVALID_VERIFICATION_CODE.getMessage());
            }
        }
        return checkMemberSmsCount;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;  // 100000 ~ 999999 범위의 숫자
        return String.valueOf(code);
    }

    @Override
    public boolean checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO) {
        SmsDTO smsDTO = SmsDTO.builder()
                .phoneFirst(findIdRequestDTO.getPhoneFirst())
                .phoneMiddle(findIdRequestDTO.getPhoneMiddle())
                .phoneLast(findIdRequestDTO.getPhoneLast())
                .phoneVerificationCode(findIdRequestDTO.getPhoneVerificationCode()).build();
        List<MemberSms> memberSmsList = memberSmsMapper.select(smsDTO);

        if (memberSmsList.isEmpty()) {
            return false;
        } else {
            for (MemberSms memberSms : memberSmsList) {
                if (smsDTO.getPhoneLast().equals(encryptionService.decrypt(memberSms.getPhoneLast()))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkEmailVerificationCode(String emailVerificationCode) {
        return true;
    }

    @Override
    public boolean checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO) {
        return true;
    }

    @Override
    public boolean checkVerifyCodeForFindSignup(MemberDTO memberDTO) {
        return true;
    }
}
