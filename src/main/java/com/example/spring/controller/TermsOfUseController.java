package com.example.spring.controller;

import com.example.spring.dto.TermsOfUseDTO;
import com.example.spring.service.TermsOfUseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/terms-of-use")
@Slf4j
public class TermsOfUseController {
    private final TermsOfUseService termsOfUseService;

    @Autowired
    public TermsOfUseController(TermsOfUseService termsOfUseService) {
        this.termsOfUseService = termsOfUseService;
    }

    @GetMapping
    public ResponseEntity<List<TermsOfUseDTO>> getTermsOfUse() {
        return ResponseEntity.ok(termsOfUseService.getTermsOfUse());
    }
}
