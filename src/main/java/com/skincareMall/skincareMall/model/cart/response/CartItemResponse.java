package com.skincareMall.skincareMall.model.cart.response;

import com.skincareMall.skincareMall.entity.ProductVariant;
import com.skincareMall.skincareMall.model.product.response.ProductResponse;
import com.skincareMall.skincareMall.model.product.response.ProductVariantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private BigDecimal total;
    private Boolean isActive;
    private CartProductResponse product;
    private Long quantity;
    private CartProductVariantResponse productVariant;
}
