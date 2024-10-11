package com.skincareMall.skincareMall.model.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateCategoryRequest {
    @Size(max = 100)
    private String name;
    private String categoryImage;
    private List<UpdateCategoryItemRequest> categoryItemRequests;
}
