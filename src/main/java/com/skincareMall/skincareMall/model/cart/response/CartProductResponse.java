package com.skincareMall.skincareMall.model.cart.response;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProductResponse {
    private String productId;
    private String productName;
    private String thumbnailImage;
    private String brandName;
    private String categoryName;
}
