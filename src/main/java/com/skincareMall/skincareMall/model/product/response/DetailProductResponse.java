package com.skincareMall.skincareMall.model.product.response;

import com.skincareMall.skincareMall.entity.ProductImage;
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
public class DetailProductResponse {
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
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<ProductVariantResponse> productVariants;
}
