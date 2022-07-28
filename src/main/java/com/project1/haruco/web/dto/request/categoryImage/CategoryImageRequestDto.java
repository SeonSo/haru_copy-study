package com.project1.haruco.web.dto.request.categoryImage;

import com.project1.haruco.web.domain.challenge.CategoryName;
import lombok.Getter;

@Getter
public class CategoryImageRequestDto {
    String categoryImageUrl;
    CategoryName categoryName;
}
