package com.project1.haruco.web.controller;

import com.project1.haruco.service.CategoryImageService;
import com.project1.haruco.web.domain.challenge.CategoryName;
import com.project1.haruco.web.dto.request.categoryImage.CategoryImageRequestDto;
import com.project1.haruco.web.dto.response.category.CategoryImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryImageController {

    private final CategoryImageService categoryImageService;

    @PostMapping("/api/category-image")
    public void postCategoryImage(@RequestBody CategoryImageRequestDto requestDto) {
        categoryImageService.postCategoryImage(requestDto);
    }

    @GetMapping("/api/category-image/{categoryName}")
    public CategoryImageResponseDto getCategoryImage(@PathVariable CategoryName categoryName) {
        return categoryImageService.getCategoryImage(categoryName);
    }
}
