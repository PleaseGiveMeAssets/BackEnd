package com.example.spring.controller;

import com.example.spring.dto.InterestCategoryDTO;
import com.example.spring.service.InterestCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Slf4j
public class InterestCategoryController {

    private final InterestCategoryService interestCategoryService;

    public InterestCategoryController(InterestCategoryService interestCategoryService) {
        this.interestCategoryService = interestCategoryService;
    }

    // 메인 카테고리 목록 불러오기
    @GetMapping("")
    public List<InterestCategoryDTO> getAllMainCategories() {
        log.info("메인 카테고리 전체 조회 요청");
        return interestCategoryService.getAllMainCategories();
    }

    // 사용자가 등록한 관심 세부 카테고리 불러오기
    @GetMapping("/interest")
    public List<InterestCategoryDTO> getUserSubCategories(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("사용자가 등록한 관심 세부 카테고리 조회 요청 - userId: {}", userDetails.getUsername());
        return interestCategoryService.getUserSubCategories(userDetails.getUsername());
    }

    // 세부 카테고리 삭제
    @DeleteMapping("/{userId}/{subCategoryId}")
    public String deleteSubCategory(@PathVariable String userId, @PathVariable int subCategoryId) {
        log.info("카테고리 삭제 요청 - userId: {}, subCategoryId: {}", userId, subCategoryId);
        int result = interestCategoryService.deleteSubCategory(userId, subCategoryId);
        String message = result > 0 ? "삭제 성공" : "삭제 실패";
        log.info("카테고리 삭제 결과 - message: {}", message);
        return message;
    }

    // 메인 카테고리에 따른 세부 카테고리 목록 불러오기
    @GetMapping("/subcategory/{mainCategoryId}")
    public List<InterestCategoryDTO> getSubCategoriesByMainCategoryId(@PathVariable int mainCategoryId) {
        log.info("세부 카테고리 조회 요청 - mainCategoryId: {}", mainCategoryId);
        return interestCategoryService.getSubCategoriesByMainCategoryId(mainCategoryId);
    }

    // 사용자가 선택한 세부 카테고리 저장
    @PostMapping("/interest/{subCategoryId}")
    public String saveInterestCategory(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable int subCategoryId) {
        String userId = userDetails.getUsername();
        log.info("관심 항목 저장 요청 - userId: {}, subCategoryId: {}", userId, subCategoryId);
        interestCategoryService.saveInterestCategory(userId, subCategoryId);
        return "관심 항목에 추가되었습니다.";
    }
}
