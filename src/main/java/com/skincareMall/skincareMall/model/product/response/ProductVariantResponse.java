package com.skincareMall.skincareMall.model.product.response;


import com.skincareMall.skincareMall.entity.ProductImage;
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
    private String size;
    private Long stok;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private Long id;
    private List<ProductImageResponse> productImages;
}
