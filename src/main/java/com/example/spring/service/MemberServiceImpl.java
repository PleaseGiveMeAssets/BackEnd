package com.example.spring.service;

import com.example.spring.domain.TermsOfUse;
import com.example.spring.domain.Member;
import com.example.spring.dto.*;
import com.example.spring.exception.*;
import com.example.spring.mapper.TermsOfUseMapper;
import com.example.spring.mapper.MemberMapper;
import com.example.spring.security.util.JwtProcessor;
import com.example.spring.service.oauth.KaKaoOauthServiceImpl;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    private MemberMapper memberMapper;
    private TermsOfUseMapper termsOfUseMapper;
    private PasswordEncoder passwordEncoder;
    private SmsService smsService;
    private JwtProcessor jwtProcessor;

    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private KaKaoOauthServiceImpl kaKaoOauthServiceImpl;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, TermsOfUseMapper termsOfUseMapper, PasswordEncoder passwordEncoder, SmsService smsService, JwtProcessor jwtProcessor) {
        this.memberMapper = memberMapper;
        this.termsOfUseMapper = termsOfUseMapper;
        this.passwordEncoder = passwordEncoder;
        this.smsService = smsService;
        this.jwtProcessor = jwtProcessor;
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
        checkDuplicatedMemberId(memberDTO.getMemberId());

        // 비밀번호 체크
        checkPassword(memberDTO);

        // 이메일 체크
        checkEmailVerificationCode(memberDTO.getEmailVerificationCode());

        // 회원가입
        log.info("핸드폰 번호 마지막 부분 암호화 시작 - 원본: {}", memberDTO.getPhoneLast());
        String encryptedPhoneLast = encryptionService.encrypt(memberDTO.getPhoneLast());
        log.info("핸드폰 번호 마지막 부분 암호화 완료 - 암호화된 값: {}", encryptedPhoneLast);

        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        memberDTO.setPhoneLast(encryptedPhoneLast);

        return memberMapper.insertMember(memberDTO);
    }

    @Override
    public int socialSignup(SocialMemberDTO socialMemberDTO) {
        return memberMapper.insertSocialMember(socialMemberDTO);
    }

    // 아이디 찾기
    @Override
    public List<Map<String, Object>> findIdByNameAndPhone(FindIdRequestDTO findIdRequestDTO) {
        if (log.isInfoEnabled()) {
            log.info("findIdByNameAndPhone findIdRequestDTO : {}", findIdRequestDTO.toString());
        }

        //응답코드 확인
        checkVerifyCodeForFindId(findIdRequestDTO);

        // DB에서 이름, phoneFirst, phoneMiddle로 회원 정보 조회
        List<Member> members = memberMapper.findMemberByNameAndPhone(findIdRequestDTO);
        if (members.isEmpty()) {
            System.out.println("MEMBER 없다");
            throw new NoMatchingMemberException(ResultCodeEnum.NO_MATCHING_MEMBER.getMessage());
        }

        // 핸드폰 번호 마지막 부분 복호화
        String decryptedPhoneLast = encryptionService.decrypt(members.get(0).getPhoneLast());
        log.info("입력한 핸드폰 번호 마지막 자리: {}", findIdRequestDTO.getPhoneLast());
        log.info("MEMBER 핸드폰 번호 마지막 자리 복호화: {}", decryptedPhoneLast);

        if (!findIdRequestDTO.getPhoneLast().equals(decryptedPhoneLast)) {
            System.out.println("복호화된 뒷자리 다르다");
            throw new NoMatchingMemberException(ResultCodeEnum.NO_MATCHING_MEMBER.getMessage());
        }
        // 회원 목록에서 입력된 phoneLast와 일치하는 회원 찾기
        List<Map<String, Object>> results = new ArrayList<>();

        for (Member member : members) {
            Map<String, Object> result = new HashMap<>();
            result = new HashMap<>();
            result.put("memberId", member.getMemberId());
            result.put("createdAt", member.getCreatedAt());

            if (member.getSns() == null || member.getSns().isEmpty()) {
                result.put("sns", null);
            } else if ("kakao".equals(member.getSns())) {
                result.put("sns", "kakao");
            } else {
                result.put("sns", "naver");
            }

            results.add(result);
        }
        System.out.println("결과: " + results);
        return results;
    }

    // 비밀번호 찾기
    @Override
    public void findPassword(FindPasswordRequestDTO findPasswordRequestDTO) {
        if (log.isInfoEnabled()) {
            log.info("findPassword findPasswordRequestDTO : {}", findPasswordRequestDTO.toString());
        }

        //응답코드 확인
        checkVerifyCodeForFindPassword(findPasswordRequestDTO);

        // 아이디, 이름, 휴대폰번호로 유저 정보가 존재하는지 조회
        List<Member> members = memberMapper.selectMemberByIdAndNameAndPhone(findPasswordRequestDTO);
        if (members.isEmpty()) {
            throw new NoMatchingMemberException(ResultCodeEnum.NO_MATCHING_MEMBER.getMessage());
        }

        boolean isPasswordChanged = false;

        for(Member user: members) {

            String decryptedPhoneLast = encryptionService.decrypt(user.getPhoneLast());

            if(decryptedPhoneLast.equals(findPasswordRequestDTO.getPhoneLast())) {
                ChangePasswordRequestDTO changePasswordRequestDTO = new ChangePasswordRequestDTO();
                changePasswordRequestDTO.setMemberId(user.getMemberId());
                changePasswordRequestDTO.setPassword(findPasswordRequestDTO.getPassword());
                changePasswordRequestDTO.setPasswordConfirmation(findPasswordRequestDTO.getPasswordConfirmation());
                changePassword(changePasswordRequestDTO);
                isPasswordChanged = true;
                break;
            }
        }

        if(!isPasswordChanged) {
            throw new NoMatchingMemberException(ResultCodeEnum.NO_MATCHING_MEMBER.getMessage());
        }
    }

    // 비밀번호 변경
    @Override
    public int changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        String newPassword = changePasswordRequestDTO.getPassword();
        if(newPassword.equals(changePasswordRequestDTO.getPasswordConfirmation())) {
            String password = passwordEncoder.encode(newPassword);
            memberMapper.updatePasswordById(changePasswordRequestDTO.getMemberId(), password);
            return 1;
        }

        if (!changePasswordRequestDTO.getPassword().equals(changePasswordRequestDTO.getPasswordConfirmation())) {
            // TODO 익셉션이랑 결과메시지 변경할 것
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // DB에서 아이디, 이름, 휴대폰번호로 비밀번호 변경
        return memberMapper.updatePasswordById(changePasswordRequestDTO.getMemberId(), passwordEncoder.encode(changePasswordRequestDTO.getPassword()));
    }

    // 로그인
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        // 사용자 정보 조회
        Member member = memberMapper.findByMemberId(loginRequestDTO.getMemberId());
        if (member == null) {
            System.out.println("유저없음");
            throw new InvalidCredentialsException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // 계정 잠금 여부 확인
        int maxFailureCount = 5;
        if (member.getPasswordFailureCount() >= maxFailureCount) {
            System.out.println("로그인횟수초과");
            throw new PasswordMismatchException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        System.out.println("비밀번호: " + loginRequestDTO.getPassword());
        System.out.println("있는 비번: " + member.getPassword());

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
            memberMapper.incrementPasswordFailureCount(member.getMemberId());
            System.out.println("비밀번호틀림");
            throw new InvalidCredentialsException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        memberMapper.resetPasswordFailureCount(member.getMemberId()); // 비밀번호 입력 횟수 리셋~~~

        // 인증 객체 생성
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getMemberId(),
                loginRequestDTO.getPassword(),
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 성공 시 액세스 토큰과 리프레시 토큰 생성
        String accessToken = jwtProcessor.generateToken(authentication.getName());
        String refreshToken = jwtProcessor.generateRefreshToken(authentication.getName());

        // Refresh Token을 HTTP-Only 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        if (request.isSecure()) {
            refreshTokenCookie.setHttpOnly(false);
            refreshTokenCookie.setSecure(true);
        } else {
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
        }
        refreshTokenCookie.setPath("/"); // 쿠키 경로
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 쿠키 유효시간 (15일)
        response.addCookie(refreshTokenCookie);

        return new LoginResponseDTO(accessToken, null);
    }

    // 소셜로그인
    @Override
    public LoginResponseDTO socialLogin(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        // 사용자 정보 조회
        Member member = memberMapper.findByMemberId(loginRequestDTO.getMemberId());
        if (member == null) {
            System.out.println("유저없음");
            throw new InvalidCredentialsException(ResultCodeEnum.INVALID_CREDENTIALS.getMessage());
        }

        // 인증 객체 생성
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getMemberId(),
                null, // 비밀번호x - null
                authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 액세스 토큰과 리프레시 토큰 생성
        String accessToken = jwtProcessor.generateToken(authentication.getName());
        String refreshToken = jwtProcessor.generateRefreshToken(authentication.getName());

        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);

        // Refresh Token을 HTTP-Only 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        if (request.isSecure()) {
            refreshTokenCookie.setHttpOnly(false);
            refreshTokenCookie.setSecure(true);
        } else {
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
        }
        refreshTokenCookie.setPath("/"); // 쿠키 경로
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 쿠키 유효시간 (15일)
        response.addCookie(refreshTokenCookie);

        return new LoginResponseDTO(accessToken, null);
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 세션에서 Kakao 액세스 토큰 가져오기
        HttpSession session = request.getSession(false);
        String kakaoAccessToken = null;
        String kakaoLogoutUrl = null;

        if (session != null) {
            kakaoAccessToken = (String) session.getAttribute("kakaoAccessToken");
        }

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String memberId = authentication.getName();
            Member member = memberMapper.findByMemberId(memberId);

            if (member != null && MemberCodeEnum.KAKAO.getValue().equals(member.getSns())) {
                if (kakaoAccessToken != null) {
                    // Kakao 로그아웃 처리
                    kakaoLogoutUrl = kaKaoOauthServiceImpl.kakaoLogout();
                    System.out.println("@@Kakao 로그아웃 처리 완료:");
                    System.out.println(kakaoLogoutUrl);
                    // 세션에서 Kakao 액세스 토큰 제거
                    session.removeAttribute("kakaoAccessToken");
                }
            }
        }

        // 세션무효화 - JSESSIONID도 없어짐
        if (session != null) {
            session.invalidate();
        }

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        if (request.isSecure()) {
            refreshTokenCookie.setHttpOnly(false);
            refreshTokenCookie.setSecure(true);
        } else {
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
        }
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // 쿠키 즉시 만료
        response.addCookie(refreshTokenCookie);

        SecurityContextHolder.clearContext();

        System.out.println("@@로그아웃 프로세스 완료@@");

        // 응답으로 카카오 로그아웃 URL 반환
        if (kakaoLogoutUrl != null) {
            // 카카오 로그아웃 URL이 있으면 프론트에 전달
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"kakaoLogoutUrl\":\"" + kakaoLogoutUrl + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 카카오 로그아웃 URL이 없으면 일반 로그아웃 처리
            response.setStatus(HttpServletResponse.SC_OK);
        }
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

        memberDTO.getTermsAgreementDTOList().forEach(termsAgreementDTO -> {
            if (!requiredTermsOfUseIds.contains(termsAgreementDTO.getTermsOfUseId())) {
                if (MemberCodeEnum.Y.getValue().equals(termsAgreementDTO.getRequired().toString())) {
                    throw new ApplicationContextException(ResultCodeEnum.NO_EXIST_TERMS_OF_USE.getMessage());
                }
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
     * @param memberId
     */
    private void checkDuplicatedMemberId(String memberId) {
        int memberCount = memberMapper.selectMemberById(memberId);

        if (memberCount > 0) {
            throw new MemberAlreadyExistsException(ResultCodeEnum.DUPLICATED_MEMBER_ID.getMessage());
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

    // 핸드폰 번호로 회원 찾기
    public Member findByPhoneNumber(String phoneFirst, String phoneMiddle) {
        return memberMapper.findMemberByPhoneNumber(phoneFirst, phoneMiddle);
    }

    // 임시 비밀번호 생성
    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public LoginResponseDTO renewLogin(String token, HttpServletRequest request, HttpServletResponse response) {
        boolean isValidToken = jwtProcessor.validateToken(token);

        if (isValidToken) {
            String memberId = jwtProcessor.getUsername(token);
            Member member = memberMapper.findByMemberId(memberId);

            if (member == null) {
                throw new MemberIdNotFoundException(ResultCodeEnum.NO_EXIST_MEMBER_ID.getMessage());
            }

            if (log.isInfoEnabled()) {
                log.info("renewLogin member : {}", member);
            }

            if (MemberCodeEnum.Y.getValue().equals(member.getAutoLogin().toString())) {
                // 인증 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        member.getMemberId(),
                        member.getPassword() // 비밀번호x - null
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 액세스 토큰과 리프레시 토큰 생성
                String accessToken = jwtProcessor.generateToken(authentication.getName());
                String refreshToken = jwtProcessor.generateRefreshToken(authentication.getName());

                // Refresh Token을 HTTP-Only 쿠키로 설정
                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
                if (request.isSecure()) {
                    refreshTokenCookie.setHttpOnly(false);
                    refreshTokenCookie.setSecure(true);
                } else {
                    refreshTokenCookie.setHttpOnly(true);
                    refreshTokenCookie.setSecure(false);
                }
                refreshTokenCookie.setPath("/"); // 쿠키 경로
                refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // 쿠키 유효시간 (15일)
                response.addCookie(refreshTokenCookie);
                return new LoginResponseDTO(accessToken, null);
            } else {
                throw new SessionExpiredException(ResultCodeEnum.SESSION_EXPIRATION.getMessage());
            }
        } else {
            throw new SessionExpiredException(ResultCodeEnum.SESSION_EXPIRATION.getMessage());
        }
    }
}
