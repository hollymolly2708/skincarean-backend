package com.skincareMall.skincareMall.controller.category;

import com.skincareMall.skincareMall.entity.Admin;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryItemRequest;
import com.skincareMall.skincareMall.model.category.request.CreateCategoryRequest;
import com.skincareMall.skincareMall.model.category.request.UpdateCategoryRequest;
import com.skincareMall.skincareMall.model.category.response.CategoryResponse;
import com.skincareMall.skincareMall.model.category.response.DetailCategoryResponse;
import com.skincareMall.skincareMall.model.user.response.WebResponse;
import com.skincareMall.skincareMall.service.category.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;



    @PostMapping(path = "/api/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addCategory(Admin admin, @RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.addCategory(admin, createCategoryRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan kategori").build();
    }

    @PostMapping(path = "/api/categories/{categoryId}")
   public WebResponse<String> addCategoryItemByCategoryId(Admin admin,@PathVariable("categoryId") Long categoryId, @RequestBody  CreateCategoryItemRequest createCategoryItemRequest){
        categoryService.addCategoryItemByCategoryId(admin,categoryId,createCategoryItemRequest);
        return WebResponse.<String>builder().data("Berhasil menambahkan item kategori").build();
    }

    @GetMapping(path = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> allCategories = categoryService.getAllCategories();
        return WebResponse.<List<CategoryResponse>>builder().data(allCategories).build();
    }

    @GetMapping(path = "/api/categories/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<DetailCategoryResponse> getDetailCategory(@PathVariable("categoryId") Long categoryId) {
        DetailCategoryResponse response = categoryService.getDetailCategory(categoryId);
        return WebResponse.<DetailCategoryResponse>builder().data(response).build();
    }
//
    @PatchMapping(path = "/api/categories/{categoryId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<DetailCategoryResponse> updateCategory(Admin admin, @PathVariable("categoryId") Long categoryId, @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        DetailCategoryResponse response = categoryService.updateCategory(admin, categoryId, updateCategoryRequest);
        return WebResponse.<DetailCategoryResponse>builder().data(response).build();
    }

    @DeleteMapping(path = "/api/categories/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteCategory(Admin admin, @PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(admin, categoryId);
        return WebResponse.<String>builder().data("Berhasil menghapus kategori").build();
    }

}
