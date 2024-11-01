package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UpdateProductImageRequest {
    @Size(max = 255)
    @NotBlank
    private String imageUrl;
    private Long id;
    private String productVariantId;
}
