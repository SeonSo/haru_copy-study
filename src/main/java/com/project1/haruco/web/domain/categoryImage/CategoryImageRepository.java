package com.project1.haruco.web.domain.categoryImage;

import com.project1.haruco.web.domain.challenge.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long> {
    Boolean existsByCategoryImageUrl(String categoryImageUrl);
    List<CategoryImage> findAllByCategoryName(CategoryName categoryName);
    void deleteByCategoryImageUrl(String imgUrl);
}
