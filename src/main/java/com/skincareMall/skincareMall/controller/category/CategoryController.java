package com.skincareMall.skincareMall.controller.category;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryRequest;
import com.skincareMall.skincareMall.model.category.response.CategoryResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = "/api/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addCategory(Admin admin, @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.addCategory(admin, createCategoryRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan kategori").build();
    }

    @GetMapping(path = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> allCategories = categoryService.getAllCategories();
        return WebResponse.<List<CategoryResponse>>builder().data(allCategories).build();
    }

    @GetMapping(path = "/api/categories/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CategoryResponse> getDetailCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryResponse response = categoryService.getDetailCategory(categoryId);
        return WebResponse.<CategoryResponse>builder().data(response).build();
    }

    @PatchMapping(path = "/api/categories/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<CategoryResponse> updateCategory(Admin admin, @PathVariable("categoryId") Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        CategoryResponse response = categoryService.updateCategory(admin, categoryId, updateCategoryRequest);
        return WebResponse.<CategoryResponse>builder().data(response).build();
    }

    @DeleteMapping(path = "/api/categories/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteCategory(Admin admin, @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(admin, categoryId);
        return WebResponse.<String>builder().data("Berhasil menghapus kategori").build();
    }

}
