package com.example.spring.service;

import com.example.spring.domain.User;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.exception.InvalidVerificationCodeException;
import com.example.spring.exception.PasswordMismatchException;
import com.example.spring.mapper.UserMapper;
import com.example.spring.util.ResultCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;
//    private final JavaMailSender mailSender;

    // 아이디 찾기
    @Override
    public Map<String, Object> findIdByNameAndPhone(FindIdRequestDTO findIdRequestDTO) {
        if (log.isInfoEnabled()) {
            log.info("findIdByNameAndPhone findIdRequestDTO : {}", findIdRequestDTO.toString());
        }

        //응답코드 확인
        checkVerifyCodeForFindId(findIdRequestDTO);

        // DB에서 이름, phoneFirst, phoneMiddle로 회원 정보 조회
        List<User> users = userMapper.findMemberByNameAndPhone(findIdRequestDTO);
        if (users.isEmpty()) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage()); // 일치하는 회원이 없는 경우
        }

        // 휴대폰번호 뒷자리 암호화 검사
        boolean isMatches = passwordEncoder.matches(findIdRequestDTO.getPhoneLast(), users.get(0).getPhoneLast());

        if (!isMatches) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // 회원 목록에서 입력된 phoneLast와 일치하는 회원 찾기
        Map<String, Object> result = null;
        for (User user : users) {
            // 일치하는 회원 정보 반환
            result = new HashMap<>();
            result.put("userId", user.getUserId());
            result.put("createdAt", user.getCreatedAt());
        }
        return result;
    }

    @Override
    public int findPassword(FindPasswordRequestDTO findPasswordRequestDTO) {
        if (log.isInfoEnabled()) {
            log.info("findPassword findPasswordRequestDTO : {}", findPasswordRequestDTO.toString());
        }

        //응답코드 확인
        checkVerifyCodeForFindPassword(findPasswordRequestDTO);

        // 아이디, 이름, 휴대폰번호로 유저 정보가 존재하는지 조회
        User user = userMapper.selectUserByIdAndNameAndPhone(findPasswordRequestDTO);
        if (user == null) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // 휴대폰번호 뒷자리 암호화 검사
        boolean isMatches = passwordEncoder.matches(findPasswordRequestDTO.getPhoneLast(), user.getPhoneLast());
        if (!isMatches) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        if (!findPasswordRequestDTO.getPassword().equals(findPasswordRequestDTO.getPasswordConfirmation())) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // DB에서 아이디, 이름, 휴대폰번호로 비밀번호 변경
        return userMapper.updatePasswordById(findPasswordRequestDTO.getUserId(), passwordEncoder.encode(findPasswordRequestDTO.getPassword()));
    }

    // 아이디 찾기
    // 인증코드 확인 여부x
    private void checkVerifyCodeForFindId(FindIdRequestDTO findIdRequestDTO) {
        boolean isVerified = smsService.checkVerifyCodeForFindId(findIdRequestDTO);
        if (!isVerified) {
            throw new InvalidVerificationCodeException(ResultCodeEnum.INVALID_VERIFICATION_CODE.getMessage());
        }
    }

    // 비멀번호 찾기
    // 인증코드 확인 여부x
    private void checkVerifyCodeForFindPassword(FindPasswordRequestDTO findPasswordRequestDTO) {
        boolean isVerified = smsService.checkVerifyCodeForFindPassword(findPasswordRequestDTO);
        if (!isVerified) {
            throw new InvalidVerificationCodeException(ResultCodeEnum.INVALID_VERIFICATION_CODE.getMessage());
        }
    }
}
