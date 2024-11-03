package com.skincareMall.skincareMall.model.category.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryItemRequest {
    @Size(max = 100)
    @NotBlank
    @NotNull
    private String name;
    private String description;
    private String categoryItemImage;
}
