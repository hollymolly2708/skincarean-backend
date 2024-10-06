package com.skincareMall.skincareMall.service.category;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Category;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryRequest;
import com.skincareMall.skincareMall.model.category.response.CategoryResponse;
import com.skincareMall.skincareMall.repository.CategoryRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(Admin admin, CreateCategoryRequest createCategoryRequest) {
        validationService.validate(createCategoryRequest);
        Category category = new Category();
        category.setCategoryImage(createCategoryRequest.getCategoryImage());
        category.setName(createCategoryRequest.getName());
        category.setDescription(createCategoryRequest.getDescription());
        category.setCreatedAt(Utilities.changeFormatToTimeStamp());
        category.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            return CategoryResponse.builder()
                    .categoryImage(category.getCategoryImage())
                    .name(category.getName())
                    .id(category.getId())
                    .description(category.getDescription())
                    .createdAt(category.getCreatedAt())
                    .lastUpdatedAt(category.getLastUpdatedAt())
                    .build();
        }).toList();
    }

    public CategoryResponse getDetailCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryImage(category.getCategoryImage())
                .name(category.getName())
                .description(category.getDescription())
                .lastUpdatedAt(category.getLastUpdatedAt())
                .createdAt(category.getCreatedAt())
                .build();
    }

    public CategoryResponse updateCategory(Admin admin, Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
        validationService.validate(updateCategoryRequest);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));
        if(Objects.nonNull(updateCategoryRequest.getCategoryImage())){
            category.setCategoryImage(updateCategoryRequest.getCategoryImage());
        }
        if(Objects.nonNull(updateCategoryRequest.getName())){
            category.setName(updateCategoryRequest.getName());
        }
        if(Objects.nonNull(updateCategoryRequest.getDescription())){
            category.setDescription(updateCategoryRequest.getDescription());
        }
        category.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .createdAt(category.getCreatedAt())
                .id(category.getId())
                .categoryImage(category.getCategoryImage())
                .name(category.getName())
                .lastUpdatedAt(category.getLastUpdatedAt())
                .description(category.getDescription())
                .build();
    }

    public void deleteCategory(Admin admin, Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));
        categoryRepository.delete(category);
    }
}
