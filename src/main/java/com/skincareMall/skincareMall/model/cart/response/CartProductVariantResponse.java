package com.skincareMall.skincareMall.model.cart.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductVariantResponse {

    private String size;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private String thumbnailVariantImage;
    private Long id;

}
