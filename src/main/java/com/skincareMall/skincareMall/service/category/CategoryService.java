package com.skincareMall.skincareMall.service.category;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryItemRequest;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryRequest;
import com.skincareMall.skincareMall.model.category.response.CategoryResponse;
import com.skincareMall.skincareMall.model.category.response.DetailCategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(Admin admin, CreateCategoryRequest createCategoryRequest);
    void addCategoryItemByCategoryId(Admin admin, Long categoryId, CreateCategoryItemRequest createCategoryItemRequest);
    List<CategoryResponse> getAllCategories();
    DetailCategoryResponse getDetailCategory(Long categoryId);
    DetailCategoryResponse updateCategory(Admin admin, Long categoryId, UpdateCategoryRequest updateCategoryRequest);
    void deleteCategory(Admin admin, Long categoryId);
}
