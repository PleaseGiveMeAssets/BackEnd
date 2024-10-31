package com.example.spring.service;

import com.example.spring.domain.Portfolio;
import com.example.spring.domain.Member;
import com.example.spring.dto.MemberProfileDTO;
import com.example.spring.mapper.PortfolioMapper;
import com.example.spring.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberMapper memberMapper;
    private final PortfolioMapper portfolioMapper;

    @Autowired
    public MemberProfileServiceImpl(MemberMapper memberMapper, PortfolioMapper portfolioMapper) {
        this.memberMapper = memberMapper;
        this.portfolioMapper = portfolioMapper;
    }

    @Override
    public MemberProfileDTO getMemberProfile(String memberId) {
        try {
            Member memberProfile = memberMapper.selectMemberProfile(memberId);

            if (memberProfile == null) {
                log.warn("Member not found for memberId: {}", memberId);
                return null;
            }

            // 사용자 포트폴리오 정보를 가져와 자산 총액 계산
            List<Portfolio> portfolios = portfolioMapper.selectOrdersByMemberId(memberId);
            double totalAssets = portfolios.stream()
                    .mapToDouble(order -> order.getPrice() * order.getQuantity())
                    .sum();

            // Member 객체의 프로필 정보를 MemberDTO 로 변환
            MemberProfileDTO memberDTO = MemberProfileDTO.builder()
                    .memberId(memberProfile.getMemberId())
                    .nickname(memberProfile.getNickname())
                    .investmentTypeName(memberProfile.getInvestmentType() != null ? memberProfile.getInvestmentType().getInvestmentTypeName() : null)
                    .totalAssets(totalAssets)
                    .profileImageUrl(memberProfile.getProfileImageUrl())
                    .name(memberProfile.getName())
                    .build();

            log.info("Member profile and total assets retrieved: {}", memberDTO);
            return memberDTO;

        } catch (Exception e) {
            log.error("Failed to retrieve member profile for memberId: {}", memberId, e);
            return null;
        }
    }
}
