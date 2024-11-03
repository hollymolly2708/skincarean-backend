package com.skincareMall.skincareMall.model.product.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductRequest {
    @Size(max = 255)
    @NotBlank
    private String productName;
    @NotBlank
    private String productDescription;
    @Size(max = 255)
    @NotBlank
    private String thumbnailImage;
    private Boolean isPromo;
    @NotNull
    private Long brandId;
    private Boolean isPopularProduct;
    private Long categoryItemId;
    private String ingredient;
    @Size(max = 100)
    @NotBlank
    private String bpomCode;
    @NotNull
    private List<CreateProductVariantRequest> productVariantRequests;

}
