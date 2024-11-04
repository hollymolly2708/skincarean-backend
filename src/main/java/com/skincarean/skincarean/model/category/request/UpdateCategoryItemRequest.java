package com.skincarean.skincarean.model.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCategoryItemRequest {

    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    private String description;
    private String categoryItemImage;
}
