package com.skincarean.skincarean.model.category.request;

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
@NoArgsConstructor
@Builder
public class CreateCategoryRequest {
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String name;
    @Size(max = 255)
    private String categoryImage;
    private List<CreateCategoryItemRequest> categoryItemRequests;
}
