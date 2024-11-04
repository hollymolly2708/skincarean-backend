package com.skincarean.skincarean.model.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailProductResponseBySingleVariant {
    private String productId;
    private String productName;
    private String productDescription;
    private Boolean isPromo;
    private String thumbnailImage;
    private String bpomCode;
    private Long totalStok;
    private String brandName;
    private Boolean isPopularProduct;
    private String ingredient;
    private String categoryName;
    private ProductVariantResponse productVariant;

}
