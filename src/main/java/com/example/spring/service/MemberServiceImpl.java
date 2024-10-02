package com.example.spring.service;

import com.example.spring.domain.TermsOfUse;
import com.example.spring.domain.User;
import com.example.spring.dto.FindIdRequestDTO;
import com.example.spring.dto.FindPasswordRequestDTO;
import com.example.spring.dto.MemberDTO;
import com.example.spring.exception.EmailVerificationException;
import com.example.spring.exception.InvalidVerificationCodeException;
import com.example.spring.exception.PasswordMismatchException;
import com.example.spring.exception.UserAlreadyExistsException;
import com.example.spring.mapper.TermsOfUseMapper;
import com.example.spring.mapper.UserMapper;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    private UserMapper userMapper;
    private TermsOfUseMapper termsOfUseMapper;
    private PasswordEncoder passwordEncoder;
    private SmsService smsService;

    @Autowired
    public MemberServiceImpl(UserMapper userMapper, TermsOfUseMapper termsOfUseMapper, PasswordEncoder passwordEncoder, SmsService smsService) {
        this.userMapper = userMapper;
        this.termsOfUseMapper = termsOfUseMapper;
        this.passwordEncoder = passwordEncoder;
        this.smsService = smsService;
    }

    /**
     * 회원가입
     * <p>
     * 회원가입을 위한 필수체크 및 유저 정보를 입력하는 메소드이다.
     *
     * @param memberDTO
     * @return
     */
    @Override
    public int signup(MemberDTO memberDTO) {
        if (log.isInfoEnabled()) {
            log.info("signup memberDTO : {}", memberDTO);
        }

        // 이용약관 필수 체크
        checkTermsOfUse(memberDTO);

        // 휴대폰 인증 체크
        checkVerifyCodeForSignup(memberDTO);

        // 아이디 중복 체크
        checkDuplicatedUserId(memberDTO.getUserId());

        // 비밀번호 체크
        checkPassword(memberDTO);

        // 이메일 체크
        checkEmailVerificationCode(memberDTO.getEmailVerificationCode());

        // 회원가입
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        memberDTO.setPhoneLast(passwordEncoder.encode(memberDTO.getPhoneLast()));
        return userMapper.insertUser(memberDTO);
    }

    /**
     * 이용약관 체크
     * <p>
     * 이용약관 필수값들을 체크하는 메소드이다.
     *
     * @param memberDTO
     */
    private void checkTermsOfUse(MemberDTO memberDTO) {
        List<TermsOfUse> termsOfUseList = termsOfUseMapper.selectListTermsOfUseByRequired(MemberCodeEnum.Y.getValue());

        if (termsOfUseList == null || termsOfUseList.isEmpty() || memberDTO.getTermsAgreementDTOList() == null || memberDTO.getTermsAgreementDTOList().isEmpty()) {
            throw new ApplicationContextException(ResultCodeEnum.NO_EXIST_TERMS_OF_USE.getMessage());
        }

        Set<Long> requiredTermsOfUseIds = termsOfUseList.stream().map(TermsOfUse::getTermsOfUseId).collect(Collectors.toSet());

        memberDTO.getTermsAgreementDTOList().stream().forEach(termsAgreementDTO -> {
            if (!requiredTermsOfUseIds.contains(termsAgreementDTO.getTermsOfUseId())) {
                throw new ApplicationContextException(ResultCodeEnum.NO_EXIST_TERMS_OF_USE.getMessage());
            }
        });
    }

    /**
     * 휴대폰인증 확인
     * <p>
     * 휴대폰인증 API 호출하는 메소드이다.
     *
     * @param memberDTO
     */
    private void checkVerifyCodeForSignup(MemberDTO memberDTO) {
        boolean isVerified = smsService.checkVerifyCodeForFindSignup(memberDTO);
        if (!isVerified) {
            throw new ApplicationContextException(ResultCodeEnum.INVALID_VERIFICATION_CODE.getMessage());
        }
    }

    /**
     * 아이디중복 체크
     * <p>
     * 아이디로 중복 체크하는 메소드이다.
     *
     * @param userId
     */
    private void checkDuplicatedUserId(String userId) {
        int userCount = userMapper.selectUserById(userId);

        if (userCount > 0) {
            throw new UserAlreadyExistsException(ResultCodeEnum.DUPLICATED_MEMBER_ID.getMessage());
        }
    }

    /**
     * 비밀번호 체크
     * <p>
     * 비밀번호와 비밀번호
     *
     * @param memberDTO
     */
    private static void checkPassword(MemberDTO memberDTO) {
        if (!memberDTO.getPassword().equals(memberDTO.getPasswordConfirmation())) {
            throw new PasswordMismatchException(ResultCodeEnum.PASSWORD_MISMATCH.getMessage());
        }
    }

    /**
     * 이메일 체크
     * <p>
     * 이메일 인증번호를 체크하는 메소드이다.
     *
     * @param emailVerificationCode
     */
    private void checkEmailVerificationCode(String emailVerificationCode) {
        boolean isVerified = smsService.checkEmailVerificationCode(emailVerificationCode);
        if (!isVerified) {
            throw new EmailVerificationException(ResultCodeEnum.INVALID_EMAIL_VERIFICATION.getMessage());
        }
    }

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
