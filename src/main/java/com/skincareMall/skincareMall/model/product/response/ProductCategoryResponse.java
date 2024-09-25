package com.skincareMall.skincareMall.model.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryResponse {
    private String size;
    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal originalPrice;
    private Long quantity;
}
