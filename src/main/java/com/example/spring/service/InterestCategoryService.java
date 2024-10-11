package com.example.spring.service;

import com.example.spring.dto.InterestCategoryDTO;

import java.util.List;

public interface InterestCategoryService {

    // 메인 카테고리 목록 불러오기
    List<InterestCategoryDTO> getAllMainCategories();

    // 사용자가 등록한 관심 세부 카테고리 불러오기
    List<InterestCategoryDTO> getUserSubCategories(String userId);

    // 사용자가 등록한 관심 세부 카테고리 삭제
    int deleteSubCategory(String userId, int subCategoryId);

    // 메인 카테고리에 따른 세부 카테고리 목록 불러오기
    List<InterestCategoryDTO> getSubCategoriesByMainCategoryId(int mainCategoryId);

    // 사용자가 선택한 세부 카테고리 저장
    void saveInterestCategory(String userId, int subCategoryId);
}
