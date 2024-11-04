package com.skincarean.skincarean.model.product.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductVariantResponse {
    private Long id;
    private String size;
    private Long stok;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private String thumbnailVariantImage;
    private List<ProductVariantImageResponse> productVariantImages;
}
