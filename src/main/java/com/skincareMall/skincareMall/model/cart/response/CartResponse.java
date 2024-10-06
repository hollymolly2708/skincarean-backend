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
public class CartResponse {
    private Long cartId;
    private String productId;
    private BigDecimal totalPrice;
    private BigDecimal originalPrice;
    private BigDecimal discount;
    private String productName;
    private String thumbnailProduct;
    private Boolean isActive;
}
