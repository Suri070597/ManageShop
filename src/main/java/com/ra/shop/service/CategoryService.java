package com.ra.shop.service;

import com.ra.shop.model.dto.CategoryDTO;
import com.ra.shop.model.dto.CategoryRequest;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO createCategory(CategoryRequest categoryRequest);

    CategoryDTO updateCategory(Long id, CategoryRequest categoryRequest);

    CategoryDTO changeCategoryStatus(Long id);
}
