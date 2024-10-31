package com.skincareMall.skincareMall.model.product.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
}
