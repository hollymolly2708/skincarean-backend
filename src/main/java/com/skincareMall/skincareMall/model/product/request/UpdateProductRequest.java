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
public class UpdateProductRequest {
    @Size(max = 100)
    private String productName;
    @Size(max = 100)
    private String productDescription;
    @Size(max = 255)
    private String thumbnailImage;
    private Boolean isPromo;
    private Long brandId;
    private Long categoryItemId;
    @Size(max = 100)
    @Size(max = 255)
    private String ingredient;
    private String bpomCode;
    private Boolean isPopularProduct;
    private List<UpdateProductVariantRequest> productVariants;

}
