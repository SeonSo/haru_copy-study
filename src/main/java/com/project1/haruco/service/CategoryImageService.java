package com.project1.haruco.service;

import com.project1.haruco.web.domain.categoryImage.CategoryImage;
import com.project1.haruco.web.domain.categoryImage.CategoryImageRepository;
import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.dto.request.categoryImage.CategoryImageRequestDto;
import com.project1.haruco.web.dto.response.category.CategoryImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryImageService {

    private final CategoryImageRepository categoryImageRepository;

    @Transactional
    public void postCategoryImage(CategoryImageRequestDto requestDto) {
        if (!categoryImageRepository.existsByCategoryImageUrl(requestDto.getCategoryImageUrl())) {
            categoryImageRepository.save(new CategoryImage(requestDto));
        } else throw new IllegalArgumentException("이미 존재하는 이미지입니다.");
    }

    public CategoryImageResponseDto getCategoryImage(CategoryName categoryName) {
        CategoryImageResponseDto categoryImageResponseDto = new CategoryImageResponseDto();
        categoryImageRepository.findAllByCategoryName(categoryName).forEach(
                value -> categoryImageResponseDto.addCategoryImageUrl(value.getCategoryImageUrl()));
        return categoryImageResponseDto;
    }
}

