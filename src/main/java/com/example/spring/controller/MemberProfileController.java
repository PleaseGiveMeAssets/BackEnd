package com.example.spring.controller;

import com.example.spring.dto.MemberDTO;
import com.example.spring.dto.MemberProfileDTO;
import com.example.spring.service.MemberProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member/profile")
@Slf4j
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    @Autowired
    public MemberProfileController(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @GetMapping
    public ResponseEntity<MemberProfileDTO> getMemberProfile(@AuthenticationPrincipal UserDetails userDetails ) {

        MemberProfileDTO memberProfile = memberProfileService.getMemberProfile(userDetails.getUsername());

        if (memberProfile != null) {
            return new ResponseEntity<>(memberProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
