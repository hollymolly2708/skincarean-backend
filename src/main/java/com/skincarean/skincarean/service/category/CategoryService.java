package com.skincarean.skincarean.service.category;

import com.skincarean.skincarean.entity.Admin;
import com.skincarean.skincarean.model.category.request.CreateCategoryItemRequest;
import com.skincarean.skincarean.model.category.request.CreateCategoryRequest;
import com.skincarean.skincarean.model.category.request.UpdateCategoryRequest;
import com.skincarean.skincarean.model.category.response.CategoryResponse;
import com.skincarean.skincarean.model.category.response.DetailCategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(Admin admin, CreateCategoryRequest createCategoryRequest);
    void addCategoryItemByCategoryId(Admin admin, Long categoryId, CreateCategoryItemRequest createCategoryItemRequest);
    List<CategoryResponse> getAllCategories();
    DetailCategoryResponse getDetailCategory(Long categoryId);
    DetailCategoryResponse updateCategory(Admin admin, Long categoryId, UpdateCategoryRequest updateCategoryRequest);
    void deleteCategory(Admin admin, Long categoryId);
}
