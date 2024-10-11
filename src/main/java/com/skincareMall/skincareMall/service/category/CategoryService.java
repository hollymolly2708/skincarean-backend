package com.skincareMall.skincareMall.service.category;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.entity.Category;
import com.skincareMall.skincareMall.entity.CategoryItem;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryItemRequest;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryItemRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryRequest;
import com.skincareMall.skincareMall.model.category.response.CategoryItemResponse;
import com.skincareMall.skincareMall.model.category.response.CategoryResponse;
import com.skincareMall.skincareMall.model.category.response.DetailCategoryResponse;
import com.skincareMall.skincareMall.repository.CategoryItemRepository;
import com.skincareMall.skincareMall.repository.CategoryRepository;
import com.skincareMall.skincareMall.utils.Utilities;
import com.skincareMall.skincareMall.validation.ValidationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    @Autowired
    private ValidationService validationService;
    @Autowired
    private CategoryItemRepository categoryItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory(Admin admin, CreateCategoryRequest createCategoryRequest) {
        validationService.validate(createCategoryRequest);
        Category category = new Category();
        category.setName(createCategoryRequest.getName());
        category.setCreatedAt(Utilities.changeFormatToTimeStamp());
        category.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
        category.setCategoryImage(createCategoryRequest.getCategoryImage());
        categoryRepository.save(category);

        if (Objects.nonNull(createCategoryRequest.getCategoryItemRequests())) {

            List<CategoryItem> categoryItems = createCategoryRequest.getCategoryItemRequests()
                    .stream()
                    .map(createCategoryItemRequest -> CategoryItem
                            .builder()
                            .category(category)
                            .name(createCategoryItemRequest.getName())
                            .description(createCategoryItemRequest.getDescription())
                            .createdAt(Utilities.changeFormatToTimeStamp())
                            .lastUpdatedAt(Utilities.changeFormatToTimeStamp())
                            .categoryImage(createCategoryItemRequest.getCategoryItemImage())
                            .build()).toList();
            category.setCategoryItems(categoryItems);

            for (CategoryItem categoryItem : categoryItems) {
                categoryItemRepository.save(categoryItem);
            }

        }

    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponse> categoryResponses = categories.stream().map(category -> CategoryResponse.builder()

                .id(category.getId())
                .name(category.getName())
                .categoryImage(category.getCategoryImage())
                .createdAt(category.getCreatedAt())
                .lastUpdatedAt(category.getLastUpdatedAt())
                .build()).toList();

        return categoryResponses;


    }

    public DetailCategoryResponse getDetailCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));
        List<CategoryItemResponse> categoryItemResponses = categoryItemRepository.findAllByCategoryId(categoryId).stream().map(categoryItem -> CategoryItemResponse
                .builder()
                .description(categoryItem.getDescription())
                .name(categoryItem.getName())
                .id(categoryItem.getId())
                .build()).toList();

        return DetailCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryImage(category.getCategoryImage())
                .categoryItems(categoryItemResponses)
                .lastUpdatedAt(category.getLastUpdatedAt())
                .createdAt(category.getCreatedAt())
                .build();
    }

    public DetailCategoryResponse updateCategory(Admin admin, Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
        validationService.validate(updateCategoryRequest);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));

        if (Objects.nonNull(updateCategoryRequest.getCategoryImage())) {
            category.setCategoryImage(updateCategoryRequest.getCategoryImage());
        }
        if (Objects.nonNull(updateCategoryRequest.getName())) {
            category.setName(updateCategoryRequest.getName());
        }
        categoryRepository.save(category);
        if (Objects.nonNull(updateCategoryRequest.getCategoryItemRequests())) {
            for (UpdateCategoryItemRequest updateCategoryItemRequest : updateCategoryRequest.getCategoryItemRequests()) {
                CategoryItem categoryItem = categoryItemRepository.findByIdAndCategoryId(updateCategoryItemRequest.getId(), categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori item tidak ditemukan"));
                categoryItem.setName(updateCategoryRequest.getName());
                categoryItem.setDescription(updateCategoryItemRequest.getDescription());
                categoryItem.setLastUpdatedAt(Utilities.changeFormatToTimeStamp());
                categoryItemRepository.save(categoryItem);
            }


        }

        List<CategoryItemResponse> categoryItemResponses = category.getCategoryItems().stream().map(categoryItem ->
                        CategoryItemResponse
                                .builder()
                                .description(categoryItem.getDescription())
                                .name(categoryItem.getName())
                                .id(categoryItem.getId())
                                .build())
                .toList();
        return DetailCategoryResponse.builder()
                .name(category.getName())
                .id(category.getId())
                .categoryImage(category.getCategoryImage())
                .lastUpdatedAt(category.getLastUpdatedAt())
                .createdAt(category.getCreatedAt())
                .categoryItems(categoryItemResponses)
                .build();
    }

    public void deleteCategory(Admin admin, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kategori tidak ditemukan"));
        categoryRepository.delete(category);
    }
}
