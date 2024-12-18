package com.example.spring.service.oauth;

import com.example.spring.domain.Member;
import com.example.spring.dto.LoginRequestDTO;
import com.example.spring.dto.LoginResponseDTO;
import com.example.spring.dto.SocialMemberDTO;
import com.example.spring.exception.SocialOauthException;
import com.example.spring.service.EncryptionService;
import com.example.spring.service.MemberServiceImpl;
import com.example.spring.util.MemberCodeEnum;
import com.example.spring.util.ResultCodeEnum;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NaverOauthServiceImpl implements NaverOauthService {
    private final EncryptionService encryptionService;
    private final MemberServiceImpl memberService;

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.clinet-secret}")
    private String CIIENT_SECRET;

    @Value("${naver.redirect-uri}")
    private String REDIRECT_URI;


    public NaverOauthServiceImpl(MemberServiceImpl memberService, EncryptionService encryptionService) {
        this.memberService = memberService;
        this.encryptionService = encryptionService;
    }

    @Override
    public String getNaverAccessToken(String authorize_code, String state) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=").append(CLIENT_ID);
            sb.append("&client_secret=").append(CIIENT_SECRET);
            sb.append("&redirect_uri=").append(REDIRECT_URI);
            sb.append("&code=").append(authorize_code);
            sb.append("&state=").append(state);
            bw.write(sb.toString());
            bw.flush();

            System.out.println("ACCESS TOKEN 요청 URL : " + sb);

            int responseCode = conn.getResponseCode();
            System.out.println("NAVER ACCESS TOKEN 응답 코드 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("네이버 응답 body 의 내용 : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            JsonObject jsonObject = element.getAsJsonObject();
            if (jsonObject.has("access_token") && jsonObject.has("refresh_token")) {
                access_Token = jsonObject.get("access_token").getAsString();
                refresh_Token = jsonObject.get("refresh_token").getAsString();

                System.out.println("access_token : " + access_Token);
                System.out.println("refresh_token : " + refresh_Token);
            } else {
                System.out.println("토큰 정보가 없습니다.");
            }

            br.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("네트워크 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("예상치 못한 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return access_Token;
    }

    @Override
    public JsonObject getMemberInfo(String access_Token) {
        String reqURL = "https://openapi.naver.com/v1/nid/me"; // Access 토큰으로 유저 정보를 요청하는 API
        JsonObject memberInfo = new JsonObject();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("유저 정보 요청 응답 코드 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("유저 정보 요청 응답 Body 의 내용 : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());
            JsonObject response = element.getAsJsonObject();

            System.out.println(response.toString());

            if (response.has("response")) {
                JsonObject memberResponse = response.getAsJsonObject("response");
                if (memberResponse.has("id") && memberResponse.has("email") && memberResponse.has("mobile") && memberResponse.has("name")) {
                    String id = memberResponse.get("id").getAsString();
                    String email = memberResponse.get("email").getAsString();
                    String mobile = memberResponse.get("mobile").getAsString();
                    String name = memberResponse.get("name").getAsString();

                    // 전화번호에서 하이픈 제거
                    String phoneNumber = mobile.replaceAll("-", "");

                    memberInfo.addProperty("memberId", id);
                    memberInfo.addProperty("email", email);
                    memberInfo.addProperty("phoneNumber", phoneNumber);
                    memberInfo.addProperty("name", name);

                    System.out.println("네이버 ID : " + id);
                    System.out.println("네이버 이메일 : " + email);
                    System.out.println("네이버 전화번호 : " + phoneNumber);
                    System.out.println("네이버 이름 : " + name);
                }
            }

            br.close(); // 자원 닫기
        } catch (IOException e) {
            System.out.println("네트워크 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("예상치 못한 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return memberInfo;  // 오류 발생 시에도 수집된 정보 반환
    }

    @Override
    public LoginResponseDTO processNaverLogin(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        String accessToken = getNaverAccessToken(code, state);
        JsonObject memberInfo = getMemberInfo(accessToken);

        System.out.println("memberInfo2: " + memberInfo.toString());

        if (memberInfo != null) {
            String memberId = memberInfo.get("memberId").getAsString();
            String email = memberInfo.get("email").getAsString();
            String phoneNumber = memberInfo.get("phoneNumber").getAsString();
            String name = memberInfo.get("name").getAsString();

            String phoneFirst = phoneNumber.substring(0, 3);
            String phoneMiddle = phoneNumber.substring(3, 7);
            String phoneLast = phoneNumber.substring(7);

            Member existingMember = memberService.findByPhoneNumber(phoneFirst, phoneMiddle);
            boolean isExistingMember = false;


            System.out.println("memberInfo 안 비었돠");
            if (existingMember != null) {
                String decryptedPhoneLast = encryptionService.decrypt(existingMember.getPhoneLast());
                if (decryptedPhoneLast.equals(phoneLast)) {
                    if (MemberCodeEnum.NAVER.getValue().equals(existingMember.getSns())) {
                        //기존회원
                        memberId = existingMember.getMemberId();
                        isExistingMember = true;
                    } else if (MemberCodeEnum.KAKAO.getValue().equals(existingMember.getSns())) {
                        System.out.println("카카오 계정 있움");
                        throw new SocialOauthException(ResultCodeEnum.KAKAO_ACCOUNT_ALREADY_EXISTS.getMessage());
                    } else {
                        System.out.println("일반 회원 계정 있음");
                        throw new SocialOauthException(ResultCodeEnum.ACCOUNT_ALREADY_EXISTS.getMessage());
                    }
                }
            }

            if (!isExistingMember) {
                SocialMemberDTO socialMemberDTO = new SocialMemberDTO();
                socialMemberDTO.setMemberId(memberId);
                socialMemberDTO.setEmail(email);
                socialMemberDTO.setName(name);
                socialMemberDTO.setPhoneFirst(phoneFirst);
                socialMemberDTO.setPhoneMiddle(phoneMiddle);
                socialMemberDTO.setSns(MemberCodeEnum.NAVER.getValue());

                String encryptedPhoneLast = encryptionService.encrypt(phoneLast);
                socialMemberDTO.setPhoneLast(encryptedPhoneLast);

                memberService.socialSignup(socialMemberDTO);

                System.out.println("네이버 회원가입 완료");

            }

            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(memberId, null);
            LoginResponseDTO loginResponse = memberService.socialLogin(loginRequestDTO, request, response);
            System.out.println("로그인 완료");
            return loginResponse;
        }
        throw new SocialOauthException(ResultCodeEnum.NAVER_LOGIN_FAIL.getMessage());
    }
}